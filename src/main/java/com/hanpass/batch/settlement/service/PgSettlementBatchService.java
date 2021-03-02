package com.hanpass.batch.settlement.service;

import com.hanpass.batch.common.entity.*;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import com.hanpass.batch.common.repository.*;
import com.hanpass.batch.common.type.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    @Value("${hanpass.llp.reconciliation-file.url}")
    private String llpReconciliationFileUrl;

    @Value("${hanpass.llp.reconciliation-file.save.path}")
    private String llpReconciliationSavePath;

    private final RestTemplate restTemplate;
    private final XeExchangeRateRepository xeExchangeRateRepository;
    private final DepositHistRepositorySupport depositHistRepositorySupport;
    private final PaymentRepository paymentRepository;
    private final PgDailyReportRepository pgDailyReportRepository;
    private final QueenbeeDailyReportRepository queenbeeDailyReportRepository;
    private final HanpassPgSettlementRepository hanpassPgSettlementRepository;
    private final LlpDailyReportRepository llpDailyReportRepository;
    private final LlpFxRepository llpFxRepository;

    /**
     * 1. hanpass pg api server 에 LianLianPay Reconciliation file 요청
     * 2. LianLianPay Reconciliation file 저장
     * 3. LianLianPay Reconciliation data db 저장
     * todo : [llp] LianLianPay Reconciliation batch 실행 시간 확인 필요
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void saveLlpReconciliationFile() {
        try {
            // todo : settlementDate localdate - 1 로 수정 필요
            String settlementDate = DateTimeFormatter.ofPattern("yyyyMMdd")
                    .format(LocalDate.of(2020, 12, 15));
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromHttpUrl(llpReconciliationFileUrl)
                    .path(settlementDate);
            System.out.println(uriBuilder.toString());
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<ByteArrayResource> response =
                    restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, ByteArrayResource.class);
            String saveFileName = String.format("llp_reconciliation_%s.csv", settlementDate);
            File reconciliationFile = new File(llpReconciliationSavePath, saveFileName);
            if (!reconciliationFile.exists()) {
                reconciliationFile.getParentFile().mkdirs();
            }
            // save reconciliation file
            FileUtils.copyInputStreamToFile(response.getBody().getInputStream(), reconciliationFile);
            // parse reconciliation file todo : [llp]아래 하드코딩된 파일 경로 삭제 및 reconciliation 파일 요청 주석 해제 필요
//            List<LlpDailyReport> llpDailyReports =
//                    LlpDailyReport.convertLlpReconciliationFileToLlpDailyReport(reconciliationFile.getAbsolutePath());
            List<LlpDailyReport> llpDailyReports =
                    LlpDailyReport.convertLlpReconciliationFileToLlpDailyReport(
                            "/Users/sjahn/hanpass/1.hanpass/3.서비스/3.학자금결제/4.lianlianpay/1.doc/1.정산/reconciliation_sample.csv");
            // fx 거래만 filtering
            llpDailyReports = llpDailyReports.stream()
                    .filter(llpDailyReport -> llpDailyReport.getLlpTrxType() == LlpTrxType.FX)
                    .collect(Collectors.toList());
            // save pg daily report
            BigDecimal totalUsdAmount = llpDailyReports.stream()
                    .map(LlpDailyReport::getTargetAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            PgDailyReport pgDailyReport = PgDailyReport.builder()
                    .pgCompanyType(PgCompanyType.LIAN_LIAN_PAY)
                    .pgReportId(FilenameUtils.getBaseName(saveFileName))
                    .pgDailyReportDataType(PgDailyReportDataType.PAYMENT_COMPLETE)
                    .reportBeginTime(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN)) // todo : 정산기준일 수정 필요
                    .reportEndTime(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX))
                    .totalCount(llpDailyReports.size())
                    .totalAmountCurrencyCode(CurrencyCode.USD)
                    .totalAmount(totalUsdAmount)
                    .reportDate(LocalDate.now())
                    .build();
            PgDailyReport savedPgDailyReport = pgDailyReportRepository.save(pgDailyReport);
            // save llp daily report
            llpDailyReports.forEach(llpDailyReport -> llpDailyReport.setPgDailyReport(savedPgDailyReport));
            llpDailyReportRepository.saveAll(llpDailyReports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 한패스 - LianLianPay 정산
     * 정산 대상 :
     * 정산 기준 : 결제완료(환전요청)
     * 정산 환율 : Fx(환전) 요청 시 환율
     * 배치 실행 시간 :
     * LLP -> 한패스 정산금액 입금일 :
     * 타임존 :
     */
    @Transactional(rollbackFor = Exception.class)
    public void llpSettlement() {
        // 한패스 llp_fx 에서 정산 대상 조회
        LocalDate today = LocalDate.now();
        List<LlpFx> llpFxList = llpFxRepository.findByLlpFxStatusAndFxDate(
                LlpFxStatus.COMPLETE,
                today.minusDays(1l));
        // 한패스가 llp 에 정산 요청한 항목을 한패스 PG 정산 테이블에 저장
        LocalDateTime settlementBeginTime = LocalDateTime.of(today.minusDays(1), LocalTime.MIN);
        LocalDateTime settlementEndTime = LocalDateTime.of(today.minusDays(1), LocalTime.MAX);
        List<HanpassPgSettlement> hanpassPgSettlements = llpFxList.stream()
                .map(llpFx -> {
                    // 정산 상태 저장
                    Payment payment = llpFx.getPayment();
                    payment.setSettlementStatus(SettlementStatus.PROGRESS_PG_SETTLEMENT);
                    return HanpassPgSettlement.createHanpassPgSettlement(
                            payment,
                            llpFx,
                            today,
                            settlementBeginTime,
                            settlementEndTime);
                }).collect(Collectors.toList());
        hanpassPgSettlements = hanpassPgSettlementRepository.saveAll(hanpassPgSettlements);
        // llp report 데이터와 한패스 정산데이터 비교
        PgDailyReport pgDailyReport =
                pgDailyReportRepository.findByReportDateAndPgCompanyType(today, PgCompanyType.LIAN_LIAN_PAY)
                        .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                                "There is no PgDailyReport of LLP"));
        List<LlpDailyReport> llpDailyReports =
                llpDailyReportRepository.findByPgDailyReport(pgDailyReport);
        for (LlpDailyReport llpDailyReport : llpDailyReports) {
            Optional<HanpassPgSettlement> savedHanpassPgSettlement =
                    hanpassPgSettlements.stream()
                            .filter(hanpassPgSettlement ->
                                    hanpassPgSettlement.getPgReferenceId().equals(llpDailyReport.getPgTrxId())
                            ).findAny();
            Payment payment =
                    paymentRepository.findById(savedHanpassPgSettlement.get().getPaymentId())
                            .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                                    "There is no payment"));
            LlpFx llpFx =
                    llpFxRepository.findByPayment(payment)
                            .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                                    "There is no llpFx "));
            if (savedHanpassPgSettlement.isPresent()) {
                BigDecimal llpLocalAmount = llpDailyReport.getSourceAmount();
                BigDecimal llpUsdAmount = llpDailyReport.getTargetAmount();
                BigDecimal hpsUsdAmount = llpFx.getFxUsdAmount();
                if (llpUsdAmount.compareTo(hpsUsdAmount) == 0) {
                    // llp Daily Report 와 한패스 정산 DB의 금액이 일치 하는 경우
                    savedHanpassPgSettlement.get().updateValidSettlement(
                            llpDailyReport.getRate(),
                            PgCompanyType.LIAN_LIAN_PAY.getPgFeeRate(),
                            llpLocalAmount);
                    // PG 사 정산 완료 처리
                    payment.setSettlementStatus(SettlementStatus.COMPLETE_PG_SETTLEMENT);
                    payment.setPgSettlementCompleteDate(LocalDateTime.now());
                } else {
                    // llp Daily Report 와 한패스 정산 DB의 금액이 일치 하지 않는 경우
                    savedHanpassPgSettlement.get().updateIncorrectAmountSettlement(llpLocalAmount);
                }
            } else {
                // llp Daily Report 에만 있는 경우
                HanpassPgSettlement hanpassPgSettlement = HanpassPgSettlement.builder()
                        .pgCompanyType(PgCompanyType.LIAN_LIAN_PAY)
                        .pgCompanySettlementStatus(PgCompanySettlementStatus.READY)
                        .paymentId(payment.getPaymentId())
                        .pgReferenceId(llpDailyReport.getPgTrxId())
                        .settlementDate(today)
                        .settlementStartDate(settlementBeginTime)
                        .settlementEndDate(settlementEndTime)
                        .usdToLocalRate(llpDailyReport.getRate())
                        .pgLocalAmount(llpDailyReport.getSourceAmount())
                        .isInHpsReport(false)
                        .isInPgReport(true)
                        .isMatchedAmount(false)
                        .build();
                hanpassPgSettlementRepository.save(hanpassPgSettlement);
            }
        }
        log.info("complete LianLianPay settlement");
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
    @Transactional(rollbackFor = Exception.class)
    public void queenbeeSettlement() {
        PgCompanyType pgCompanyType = PgCompanyType.QUEENBEE;
        CurrencyCode currencyCode = CurrencyCode.JPY;

        // xe.com 11시 정산 환율 조회 (UTC 2시)
        LocalDate today = LocalDate.now();
        LocalTime settlementRateTime = LocalTime.of(2, 0, 0);
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
                    Payment payment =
                            paymentRepository.findByDepositAccount(depositHist.getDepositAccount())
                                    .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                                            "There is no payment"));
                    Student student = payment.getStudent();
                    DepositAccount depositAccount = payment.getDepositAccount();
                    // 정산 상태 저장
                    payment.setSettlementStatus(SettlementStatus.PROGRESS_PG_SETTLEMENT);
                    return HanpassPgSettlement.createHanpassPgSettlement(
                            payment,
                            depositHist,
                            today,
                            settlementBeginTime,
                            settlementEndTime,
                            settlementRateInfo.getRate());
                }).collect(Collectors.toList());
        hanpassPgSettlements = hanpassPgSettlementRepository.saveAll(hanpassPgSettlements);
        // 퀸비의 daily report 와 한패스 정산데이터 비교
        Optional<PgDailyReport> pgDailyReport =
                pgDailyReportRepository.findByReportDateAndPgCompanyType(today, PgCompanyType.QUEENBEE);
        List<QueenbeeDailyReport> queenbeeDailyReports =
                queenbeeDailyReportRepository.findByPgDailyReport(pgDailyReport.get());
        for (QueenbeeDailyReport queenbeeDailyReport : queenbeeDailyReports) {
            Optional<HanpassPgSettlement> savedHanpassPgSettlement =
                    hanpassPgSettlements.stream()
                            .filter(hanpassPgSettlement ->
                                    hanpassPgSettlement.getPgReferenceId().equals(queenbeeDailyReport.getQueenbeeDepositId())
                            ).findAny();
            if (savedHanpassPgSettlement.isPresent()) {
                BigDecimal queenbeeLocalAmount = queenbeeDailyReport.getDepositAmount();
                BigDecimal hpsLocalAmount = savedHanpassPgSettlement.get().getHpsLocalAmount();
                // Queenbee Daily Report 와 한패스 정산 DB 둘다 있는 경우
                if (queenbeeLocalAmount.compareTo(hpsLocalAmount) == 0) {
                    // Queenbee Daily Report 와 한패스 정산 DB의 금액이 일치 하는 경우
                    savedHanpassPgSettlement = savedHanpassPgSettlement.get().updateValidSettlement(
                            settlementRateInfo.getRate(),
                            pgCompanyType.getPgFeeRate(),
                            queenbeeLocalAmount);

                    // PG 사 정산 완료 유무 확인
                    String paymentId =
                            savedHanpassPgSettlement.get().getPaymentId();
                    List<HanpassPgSettlement> hanpassPgSettlementList =
                            hanpassPgSettlementRepository.findByPaymentId(paymentId);
                    BigDecimal totalSettlementLocalAmount =
                            hanpassPgSettlementList.stream()
                                    .map(HanpassPgSettlement::getLocalAmount)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                    Optional<Payment> payment =
                            paymentRepository.findById(paymentId);
                    BigDecimal estimateLocalAmount =
                            payment.get().getEstimate().getLocalPaymentAmount();

                    //TODO :: 결제테이블의 정산상태 변경시점 백오피스 입금확인 이후 ?
                    if (totalSettlementLocalAmount.compareTo(estimateLocalAmount) == 0) {
                        payment.get().setSettlementStatus(SettlementStatus.COMPLETE_PG_SETTLEMENT);
                        payment.get().setPgSettlementCompleteDate(LocalDateTime.now());
                    }

                } else {
                    // Queenbee Daily Report 와 한패스 정산 DB의 금액이 일치 하지 않는 경우
                    savedHanpassPgSettlement.get().updateIncorrectAmountSettlement(queenbeeLocalAmount);
                }
            } else {
                // Queenbee Daily Report 에만 있는 경우
                HanpassPgSettlement hanpassPgSettlement = HanpassPgSettlement.builder()
                        .pgCompanyType(pgCompanyType)
                        .pgCompanySettlementStatus(PgCompanySettlementStatus.READY)
                        .pgReferenceId(queenbeeDailyReport.getQueenbeeDepositId())
                        .settlementDate(today)
                        .settlementStartDate(settlementBeginTime)
                        .settlementEndDate(settlementEndTime)
                        .usdToLocalRate(settlementRateInfo.getRate())
                        .pgLocalAmount(queenbeeDailyReport.getDepositAmount())
                        .isInHpsReport(false)
                        .isInPgReport(true)
                        .isMatchedAmount(false)
                        .build();
                hanpassPgSettlementRepository.save(hanpassPgSettlement);
            }
        }
        log.info("complete queenbee settlement");
    }
}
