package com.hanpass.batch.settlement.service;

import com.hanpass.batch.common.entity.Estimate;
import com.hanpass.batch.common.entity.HanpassPartnerSettlement;
import com.hanpass.batch.common.entity.HanpassPgSettlement;
import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.repository.HanpassPartnerSettlementRepository;
import com.hanpass.batch.common.repository.HanpassPgSettlementRepository;
import com.hanpass.batch.common.repository.PaymentRepository;
import com.hanpass.batch.common.type.SettlementStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Package :: com.hanpass.batch.settlement.service
 * Developer :: Ahn Seong-jin
 * Date :: 2021/02/03
 * Description ::
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerSettlementBatchService {

    private final PaymentRepository paymentRepository;
    private final HanpassPgSettlementRepository hanpassPgSettlementRepository;
    private final HanpassPartnerSettlementRepository hanpassPartnerSettlementRepository;

    /**
     * 한패스 - 유웨이 정산
     * 정산 대상 : T-1 PG사 정산 완료된 건
     * 정산 기준 : PG사 정산 완료
     * 정산 환율 :
     * 배치 실행 시간 :
     * 한패스 -> 유웨이 정산금액 입금일 :
     * 타임존 :
     */
    @Transactional
    public void partnerSettlement() {
        try {
            // t-1에 pg사 정산완료 데이터 조회
            LocalDate yesterday = LocalDate.now().minusDays(1);
            LocalDateTime settlementBeginDate = LocalDateTime.of(yesterday, LocalTime.MIN);
            LocalDateTime settlementEndDate = LocalDateTime.of(yesterday, LocalTime.MAX);
            List<Payment> payments =
                    paymentRepository.findBySettlementStatusAndPgSettlementCompleteDateBetween(
                            SettlementStatus.COMPLETE_PG_SETTLEMENT,
                            settlementBeginDate, settlementEndDate
                    );
            List<String> paymentIds =
                    payments.stream()
                            .map(payment -> payment.getPaymentId())
                            .collect(Collectors.toList());
            List<HanpassPgSettlement> hanpassPgSettlements =
                    hanpassPgSettlementRepository.findByPaymentIdIn(paymentIds);
            // 정산환율 조회, todo : 어느시점의 한투 환율을 정산환율로 사용할지 확인 필요
            BigDecimal settlementRate = new BigDecimal(1100);
            // 파트너 정산데이터 생성
            List<HanpassPartnerSettlement> hanpassPartnerSettlements = hanpassPgSettlements.stream()
                    .collect(Collectors.groupingBy(HanpassPgSettlement::getPaymentId))
                    .entrySet().stream()
                    .map(resultMap -> {
                        Optional<Payment> payment =
                                paymentRepository.findById(resultMap.getKey());
                        List<HanpassPgSettlement> pgSettlements =
                                resultMap.getValue();
                        Estimate estimate =
                                payment.get().getEstimate();
                        // pg사에서 정산받은 usd 금액(pg사 수수료 포함)
                        BigDecimal totalPgSettlementUsdAmount =
                                pgSettlements.stream()
                                        .map(HanpassPgSettlement::getUsdAmount)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                        // pg사 수수료
                        BigDecimal totalPgFeeUsdAmount =
                                pgSettlements.stream()
                                        .map(HanpassPgSettlement::getPgFeeUsdAmount)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                        // pg사에서 정산받은 usd 금액(pg사 수수료 제외)
                        BigDecimal pgSettlementUsdAmount =
                                totalPgSettlementUsdAmount.subtract(totalPgFeeUsdAmount);
                        // pg사에서 정산받은 krw 금액(pg사 수수료 미포함)
                        BigDecimal pgSettlementKrwAmount =
                                pgSettlementUsdAmount.multiply(settlementRate);
                        // 파트너 청구원금 (krw)
                        BigDecimal partnerChargedKrwAmount =
                                estimate.getKrwPaymentAmount();
                        // 결제건에 대한 수익
                        BigDecimal totalKrwProfitAmount =
                                pgSettlementKrwAmount.subtract(partnerChargedKrwAmount);
                        // 한패스 수익(krw)
                        BigDecimal hanpassProfitKrwAmount =
                                totalKrwProfitAmount.multiply(new BigDecimal(0.6));
                        // 파트너 수익(krw)
                        BigDecimal partnerProfitKrwAmount =
                                totalKrwProfitAmount.subtract(hanpassProfitKrwAmount);
                        return HanpassPartnerSettlement.builder()
                                .paymentId(payment.get().getPaymentId())
                                .partnerTrxId(payment.get().getPartnerTrxId())
                                .requestedInstitution(payment.get().getStudent().getSchoolCode())
                                .partnerMerchantType(payment.get().getPartnerMerchantType())
                                .paymentCountryCode(estimate.getCurrency().getCountry().getCountryCode())
                                .partnerChargedKrwAmount(partnerChargedKrwAmount)
                                .pgSettlementUsdAmount(pgSettlementUsdAmount)
                                .partnerSettlementRate(settlementRate)
                                .pgSettlementKrwAmount(pgSettlementKrwAmount)
                                .totalKrwProfitAmount(totalKrwProfitAmount)
                                .hanpassProfitKrwAmount(hanpassProfitKrwAmount)
                                .partnerProfitKrwAmount(partnerProfitKrwAmount)
                                .settlementDate(yesterday.plusDays(1)) // todo : 정산일, 송금일 확인 필요
                                .partnerRemittanceDate(yesterday.plusDays(2))
                                .modId("system")
                                .build();
                    }).collect(Collectors.toList());
            // 파트너 정산데이터 저장
            hanpassPartnerSettlementRepository.saveAll(hanpassPartnerSettlements);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
