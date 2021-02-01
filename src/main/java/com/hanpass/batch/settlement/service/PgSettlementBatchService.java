package com.hanpass.batch.settlement.service;

import com.hanpass.batch.common.entity.*;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import com.hanpass.batch.common.repository.*;
import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.common.type.PgCompanySettlementStatus;
import com.hanpass.batch.common.type.PgCompanyType;
import com.hanpass.batch.common.type.PgDailyReportDataType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
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
 * Date :: 2021/01/29
 * Description ::
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PgSettlementBatchService {

    private final XeExchangeRateRepository xeExchangeRateRepository;
    private final DepositHistRepositorySupport depositHistRepositorySupport;
    private final PaymentRepository paymentRepository;
    private final PgDailyReportRepository pgDailyReportRepository;
    private final QueenbeeDailyReportRepository queenbeeDailyReportRepository;
    private final HanpassPgSettlementRepository hanpassPgSettlementRepository;

    /**
     * 한패스 - LianLianPay 정산
     * 정산 대상 :
     * 정산 기준 : 결제완료(환전요청)
     * 정산 환율 : Fx(환전) 요청 시 환율
     * 배치 실행 시간 :
     * LLP -> 한패스 정산금액 입금일 :
     * 타임존 :
     */
    @Transactional
    public void llpSettlement() {
        // llp reconciliation 파일 획득()

        //
    }

    /**
     * 한패스 - 퀸비 정산
     * 정산 대상 : T-2 15:00:00 ~ T-1 14:59:59
     * 정산 기준 : 입금통지
     * 정산 환율 : 배치실행 시점의 xe.com 11:00:00
     * 배치 실행 시간 : 11:00:00 이후
     * 퀸비 -> 한패스 정산금액 입금일 : T+1
     * 타임존 : GMT+9
     */
    @Transactional
    public void queenbeeSettlement() {
        try {
            PgCompanyType pgCompanyType = PgCompanyType.QUEENBEE;
            CurrencyCode currencyCode = CurrencyCode.JPY;

            // xe.com 11시 정산 환율 조회
            LocalDate today = LocalDate.now();
            LocalTime settlementRateTime = LocalTime.of(11, 0, 0);
            XeExchangeRate settlementRateInfo =
                    xeExchangeRateRepository.findByToCurrencyCodeAndTimestamp(
                            currencyCode,
                            LocalDateTime.of(today, settlementRateTime))
                            .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                                    String.format("There is no settlement exchange rate.(%s)", pgCompanyType.name())));
            // 한패스 DB 에서 퀸비 정산데이터 조회(입금 기준 정산)
            LocalDateTime settlementBeginTime =
                    LocalDateTime.of(today.minusDays(2l), LocalTime.of(15, 0, 0));
            LocalDateTime settlementEndTime = settlementBeginTime.plusDays(1l);
            List<DepositHist> depositHists =
                    depositHistRepositorySupport.findSettlementsOfDepositHist(
                            pgCompanyType,
                            settlementBeginTime,
                            settlementEndTime);
            // 한패스 DB의 Queenbee 정산데이터 저장
            List<HanpassPgSettlement> hanpassPgSettlements = depositHists.stream()
                    .map(depositHist -> {
                        Optional<Payment> payment =
                                paymentRepository.findByDepositAccount(depositHist.getDepositAccount());
                        return HanpassPgSettlement.builder()
                                .pgCompanyType(pgCompanyType)
                                .pgCompanySettlementStatus(PgCompanySettlementStatus.CREATION)
                                .paymentId(payment.get().getPaymentId())
                                .depositHistSeq(depositHist.getDepositHistSeq())
                                .pgReferenceId(depositHist.getPgDepositId())
                                .settlementDate(today)
                                .settlementStartDate(settlementBeginTime)
                                .settlementEndDate(settlementEndTime)
                                .usdToLocalRate(settlementRateInfo.getRate())
                                .hpsLocalAmount(depositHist.getDepositAmount())
                                .pgLocalAmount(null)
                                .localAmount(null)
                                .usdAmount(null)
                                .pgFeeLocalAmount(null)
                                .isInHpsReport(true)
                                .isInPgReport(false)
                                .isMatchedAmount(false)
                                .build();
                    }).collect(Collectors.toList());
            hanpassPgSettlements = hanpassPgSettlementRepository.saveAll(hanpassPgSettlements);
            // 퀸비의 daily report 와 한패스 정산데이터 비교
            Optional<PgDailyReport> pgDailyReport =
                    pgDailyReportRepository.findByReportDate(today);
            List<QueenbeeDailyReport> queenbeeDailyReports =
                    queenbeeDailyReportRepository.findByPgDailyReport(pgDailyReport.get());
            for (QueenbeeDailyReport queenbeeDailyReport : queenbeeDailyReports) {
                Optional<HanpassPgSettlement> savedHanpassPgSettlement = hanpassPgSettlements.stream()
                        .filter(hanpassPgSettlement ->
                                hanpassPgSettlement.getPgReferenceId().equals(queenbeeDailyReport.getQueenbeeDepositId()))
                        .findAny();
                BigDecimal queenbeeDailyReportDepositAmount = queenbeeDailyReport.getDepositAmount();
                if (savedHanpassPgSettlement.isPresent()) {
                    // Queenbee Daily Report 와 한패스 정산 DB 둘다 있는 경우
                    if (queenbeeDailyReport.getDepositAmount().compareTo(
                            savedHanpassPgSettlement.get().getHpsLocalAmount()) == 0) {
                        // Queenbee Daily Report 와 한패스 정산 DB의 금액이 일치 하는 경우
                        BigDecimal usdSettlementAmount = queenbeeDailyReportDepositAmount.divide(
                                settlementRateInfo.getRate(), new MathContext(18, RoundingMode.CEILING))
                                .setScale(8, RoundingMode.CEILING);
                        BigDecimal pgFeeLocalAmount = queenbeeDailyReportDepositAmount.multiply(
                                new BigDecimal(0.0004), new MathContext(18, RoundingMode.CEILING))
                                .setScale(8, RoundingMode.FLOOR);
                        BigDecimal pgFeeUsdAmount = pgFeeLocalAmount.divide(
                                settlementRateInfo.getRate(), new MathContext(18, RoundingMode.CEILING))
                                .setScale(8, RoundingMode.CEILING);
                        savedHanpassPgSettlement.get().setPgLocalAmount(queenbeeDailyReportDepositAmount);
                        savedHanpassPgSettlement.get().setLocalAmount(queenbeeDailyReportDepositAmount);
                        savedHanpassPgSettlement.get().setUsdAmount(usdSettlementAmount);
                        savedHanpassPgSettlement.get().setPgFeeLocalAmount(pgFeeLocalAmount);
                        savedHanpassPgSettlement.get().setPgFeeUsdAmount(pgFeeUsdAmount);
                        savedHanpassPgSettlement.get().setInPgReport(true);
                        savedHanpassPgSettlement.get().setMatchedAmount(true);
                    } else {
                        // Queenbee Daily Report 와 한패스 정산 DB의 금액이 일치 하지 않는 경우
                        savedHanpassPgSettlement.get().setPgLocalAmount(queenbeeDailyReportDepositAmount);
                        savedHanpassPgSettlement.get().setInPgReport(true);
                        savedHanpassPgSettlement.get().setMatchedAmount(false);
                    }
                } else {
                    // Queenbee Daily Report 에만 있는 경우
                    HanpassPgSettlement hanpassPgSettlement = HanpassPgSettlement.builder()
                            .pgCompanyType(pgCompanyType)
                            .pgCompanySettlementStatus(PgCompanySettlementStatus.CREATION)
                            .paymentId(null)
                            .depositHistSeq(null)
                            .pgReferenceId(queenbeeDailyReport.getQueenbeeDepositId())
                            .settlementDate(today)
                            .settlementStartDate(settlementBeginTime)
                            .settlementEndDate(settlementEndTime)
                            .usdToLocalRate(settlementRateInfo.getRate())
                            .hpsLocalAmount(null)
                            .pgLocalAmount(queenbeeDailyReportDepositAmount)
                            .localAmount(null)
                            .usdAmount(null)
                            .pgFeeLocalAmount(null)
                            .isInHpsReport(false)
                            .isInPgReport(true)
                            .isMatchedAmount(false)
                            .build();
                    hanpassPgSettlementRepository.save(hanpassPgSettlement);
                }
            }

            log.info("complete");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
