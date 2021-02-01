package com.hanpass.batch.settlement.service;

import com.hanpass.batch.common.entity.*;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import com.hanpass.batch.common.repository.*;
import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.common.type.PaymentStatus;
import com.hanpass.batch.common.type.PgCompanyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Package :: com.hanpass.batch.settlement.service
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/04
 * Description ::
 */
// todo : queenbee, llp 정산 개발 완료 후 삭제 예정
@Service
@Slf4j
@RequiredArgsConstructor
public class SettlementBatchService {

    private final PaymentRepositorySupport paymentRepositorySupport;
    private final DepositHistRepositorySupport depositHistRepositorySupport;
    private final XeExchangeRateRepository xeExchangeRateRepository;
    private final CurrencyPolicyRepository currencyPolicyRepository;
    private final SettlementRepository settlementRepository;

    @Transactional(rollbackFor = Exception.class)
    public void runSettlement() {
        try {
            llpSettlement();
            List<Settlement> settlements = queenBeeSettlement();
            settlementRepository.saveAll(settlements);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Settlement> llpSettlement() throws Exception {
        PgCompanyType pgCompanyType = PgCompanyType.LIAN_LIAN_PAY;
        CurrencyCode currencyCode = CurrencyCode.CNY;

        // 정산 대상 조회
        LocalDate settlementDate = LocalDate.now().minusDays(pgCompanyType.getSettlementTerm());
        LocalDateTime startDate = LocalDateTime.of(settlementDate, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(settlementDate, LocalTime.MAX);
        List<Payment> payments =
                paymentRepositorySupport.findSettlementList(PgCompanyType.LIAN_LIAN_PAY, PaymentStatus.COMPLETE, startDate, endDate);

        // 통화 정책 조회
        CurrencyPolicy currencyPolicy = currencyPolicyRepository.findByPgCompanyTypeAndCurrencyCode(PgCompanyType.QUEENBEE, currencyCode)
                .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                        String.format("Currency policy is not.(%s, %s)", pgCompanyType.name(), currencyCode)));

        return payments.stream()
                .map(payment -> {
                    // TODO: 2020/12/04 : pgSettlementFx 없는 경우 예외 처리 필요
                    PgSettlementFx pgSettlementFx = payment.getPgSettlementFx();
                    BigDecimal pgFeeLocalAmount = pgSettlementFx.getSettlementReqLocalAmount()
                            .divide(currencyPolicy.getUsdToKrwPgSpread()).setScale(2, RoundingMode.CEILING);
                    BigDecimal pgFeeUsdAmount = pgFeeLocalAmount
                            .divide(pgSettlementFx.getSettlementRate()).setScale(2, RoundingMode.CEILING);
                    return Settlement.builder()
                            .pgCompanyType(pgCompanyType)
                            .paymentId(payment.getPaymentId())
                            .depositHistSeq(null)
                            .settlementDate(settlementDate)
                            .remittanceDate(LocalDate.now())
                            .settlementUsdToLocalRate(pgSettlementFx.getSettlementRate())
                            .settlementLocalAmount(pgSettlementFx.getSettlementReqLocalAmount())
                            .settlementUsdAmount(pgSettlementFx.getSettlementAmount())
                            .pgFeeLocalAmount(pgFeeLocalAmount)
                            .pgFeeUsdAmount(pgFeeUsdAmount)
                            // TODO: 2020/12/04 : 한패스, 유웨이 정산 시 어느 시점의 usd to krw 환율을 사용할지 정의 필요 (krw 손익 계산 후 Uway와 수익 share)
                            .hanpassFeeAmount(BigDecimal.ZERO)
                            .partnerFeeAmount(BigDecimal.ZERO)
                            .build();
                }).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Settlement> queenBeeSettlement() throws Exception {
        PgCompanyType pgCompanyType = PgCompanyType.QUEENBEE;
        CurrencyCode currencyCode = CurrencyCode.JPY;

        // 정산 환율 조회
        LocalDate settlementDate = LocalDate.now().minusDays(2l);
        LocalDate settlementRateDate = settlementDate.plusDays(1l);
        LocalTime settlementRateTime = LocalTime.of(11, 0, 0);
        XeExchangeRate settlementRateInfo =
                xeExchangeRateRepository.findByToCurrencyCodeAndTimestamp(currencyCode, LocalDateTime.of(settlementRateDate, settlementRateTime))
                        .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                                String.format("settlement rate is not.(%s)", pgCompanyType.name())));

        // 정산 대상 조회
        LocalDateTime startDate = LocalDateTime.of(settlementDate, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(settlementDate, LocalTime.MAX);
        List<DepositHist> depositHists =
                depositHistRepositorySupport.findSettlementsOfDepositHist(pgCompanyType, startDate, endDate);

        // 통화 정책 조회
        CurrencyPolicy currencyPolicy = currencyPolicyRepository.findByPgCompanyTypeAndCurrencyCode(PgCompanyType.QUEENBEE, currencyCode)
                .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                        String.format("Currency policy is not.(%s, %s)", pgCompanyType.name(), currencyCode)));

        // TODO: 2020/12/04 : 퀸비 입금내역으로 정산하는 경우 수수료는 어떻게 차감 하는지 확인 필요
        return depositHists.stream()
                .map(depositHist -> {
                    BigDecimal settlementRate = settlementRateInfo.getRate();
                    return Settlement.builder()
                            .pgCompanyType(pgCompanyType)
                            .paymentId(null)
                            .depositHistSeq(depositHist.getDepositHistSeq())
                            .settlementDate(settlementDate)
                            .remittanceDate(LocalDate.now())
                            .settlementUsdToLocalRate(settlementRate)
                            .settlementLocalAmount(depositHist.getDepositAmount())
                            .settlementUsdAmount(depositHist.getDepositAmount().multiply(settlementRate))
                            .pgFeeLocalAmount(BigDecimal.ZERO)
                            .pgFeeUsdAmount(BigDecimal.ZERO)
                            .hanpassFeeAmount(BigDecimal.ZERO)
                            .partnerFeeAmount(BigDecimal.ZERO)
                            .build();
                }).collect(Collectors.toList());
    }
}
