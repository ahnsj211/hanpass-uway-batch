package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.common.type.LlpTrxType;
import com.opencsv.CSVReader;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.LAZY;

/**
 * Package :: com.hanpass.batch.common.entity
 * Developer :: Ahn Seong-jin
 * Date :: 2021/02/09
 * Description ::
 */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "pgDailyReport")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="llp_daily_report", indexes = {
        @Index(name = "llp_daily_report_idx1", columnList = "pg_daily_report_seq")
})
public class LlpDailyReport {

    // sequence
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long llpDailyReportSeq;

    // LlpDailyReport(n) : PgDailyReport(1)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pg_daily_report_seq")
    private PgDailyReport pgDailyReport;

    // pg사 거래 아이디
    @Column(length = 50, nullable = false)
    private String pgTrxId;

    // 거래 유형(FX, FX_CANCEL, CNY_REFUND)
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private LlpTrxType llpTrxType;

    // source 통화
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode sourceCurrencyCode;

    // source 금액
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal sourceAmount;

    // 대상 통화
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode targetCurrencyCode;

    // 대상 금액
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal targetAmount;

    // 환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal rate;

    // llp 생성일
    @Column(nullable = false)
    private LocalDateTime creationTime;

    // llp 수정일
    @Column(nullable = false)
    private LocalDateTime updateTime;

    // llp 상태
    @Column(length = 50, nullable = false)
    private String status;

    // 실패사유
    @Column(columnDefinition = "text")
    private String failReason;

    // 환불 통화
    @Column(length = 30)
    @Enumerated(STRING)
    private CurrencyCode refundCurrencyCode;

    // 환불금액
    @Column
    private BigDecimal refundAmount;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    /**
     * create LlpDailyReport
     * @param values
     * @return
     */
    public static LlpDailyReport createLlpDailyReport(String[] values) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        LlpDailyReport llpDailyReport = new LlpDailyReport();
        for (int i = 0; i < values.length; i++) {
            String value = values[i].trim();
            switch (i) {
                case 0:
                    llpDailyReport.setPgTrxId(value);
                    break;
                case 1:
                    llpDailyReport.setSourceCurrencyCode(CurrencyCode.findCurrencyCode(value).get());
                    break;
                case 2:
                    llpDailyReport.setSourceAmount(new BigDecimal(value.replace(",", "")));
                    break;
                case 3:
                    llpDailyReport.setTargetCurrencyCode(CurrencyCode.findCurrencyCode(value).get());
                    break;
                case 4:
                    llpDailyReport.setTargetAmount(new BigDecimal(value.replace(",", "")));
                    break;
                case 5:
                    llpDailyReport.setRate(new BigDecimal(value));
                    break;
                case 6:
                    llpDailyReport.setCreationTime(LocalDateTime.parse(value, dateTimeFormatter));
                    break;
                case 7:
                    llpDailyReport.setUpdateTime(LocalDateTime.parse(value, dateTimeFormatter));
                    break;
                case 8:
                    llpDailyReport.setStatus(value);
                    break;
                case 9:
                    llpDailyReport.setFailReason((StringUtils.isEmpty(value) || "null".equals(value.toLowerCase())) ?
                            null :
                            value);
                    break;
                case 10:
                    llpDailyReport.setRefundCurrencyCode((StringUtils.isEmpty(value) || "null".equals(value.toLowerCase())) ?
                            null :
                            CurrencyCode.findCurrencyCode(value).get()
                    );
                    break;
                case 11:
                    llpDailyReport.setRefundAmount((StringUtils.isEmpty(value) || "null".equals(value.toLowerCase())) ?
                            null :
                            new BigDecimal(value.replace(",", ""))
                    );
                    break;
                default:
                    break;
            }
        }
        // transaction type
        CurrencyCode sourceCurrencyCode = llpDailyReport.getSourceCurrencyCode();
        CurrencyCode targetCurrencyCode = llpDailyReport.getTargetCurrencyCode();
        if (sourceCurrencyCode == CurrencyCode.CNY &&
                targetCurrencyCode == CurrencyCode.USD) {
            llpDailyReport.setLlpTrxType(LlpTrxType.FX);
        } else if (sourceCurrencyCode == CurrencyCode.USD &&
                targetCurrencyCode == CurrencyCode.CNY) {
            llpDailyReport.setLlpTrxType(LlpTrxType.FX_CANCEL);
        } else if (sourceCurrencyCode == CurrencyCode.CNY &&
                targetCurrencyCode == CurrencyCode.CNY) {
            llpDailyReport.setLlpTrxType(LlpTrxType.CNY_REFUND);
        } else {
            llpDailyReport.setLlpTrxType(LlpTrxType.UNKNOWN);
        }
        return llpDailyReport;
    }

    /**
     * convert llp reconciliation file too LlpDailyReport
     * @param filePath
     * @return LlpDailyReport List
     * @throws ServiceException
     */
    public static List<LlpDailyReport> convertLlpReconciliationFileToLlpDailyReport(String filePath) throws ServiceException {
        String[] values = null;
        List<LlpDailyReport> llpDailyReports = new ArrayList<>();
        try(CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            csvReader.skip(1);
            while ((values = csvReader.readNext()) != null) {
                LlpDailyReport llpDailyReport = new LlpDailyReport();
                llpDailyReports.add(LlpDailyReport.createLlpDailyReport(values));
            }
            return llpDailyReports;
        } catch (Exception e) {
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
