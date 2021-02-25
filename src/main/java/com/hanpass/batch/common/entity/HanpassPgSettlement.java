package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/28
 * Description ::
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="hanpass_pg_settlement", indexes = {
        @Index(name = "hanpass_pg_settlement_idx1", columnList = "settlementDate"),
        @Index(name = "hanpass_pg_settlement_idx2", columnList = "settlementDate, isInHpsReport"),
        @Index(name = "hanpass_pg_settlement_idx3", columnList = "settlementDate, isInPgReport"),
        @Index(name = "hanpass_pg_settlement_idx4", columnList = "settlementDate, isMatchedAmount"),
        @Index(name = "hanpass_pg_settlement_idx5", columnList = "settlementDate, pgCompanySettlementStatus"),
        @Index(name = "hanpass_pg_settlement_idx6", columnList = "paymentId")
})
public class HanpassPgSettlement {

    // sequence
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long hanpassPgSettlementSeq;

    // 결제요청 일시
    @Column(nullable = true)
    private LocalDateTime paymentRequestDate;

    // 결제완료 일시
    @Column(nullable = true)
    private LocalDateTime paymentCompleteDate;

    // 청구기관(학교명)
    @Column(length = 50, nullable = true)
    private String schoolName;

    // 청구항목(파트너 상품 ID)
    @Column(length = 30, nullable = true)
    @Enumerated(STRING)
    private PartnerMerchantType partnerMerchantType;

    // 결제상태
    @Column(length = 30, nullable = true)
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;

    // 입금 상태
    @Column(length = 30, nullable = true)
    @Enumerated(STRING)
    private DepositStatus depositStatus;

    // pg사 유형
    @Column(length = 30, nullable = false)
    @Enumerated(value = STRING)
    private PgCompanyType pgCompanyType;

    // 정산 상태
    @Column(length = 30, nullable = false)
    @Enumerated(value = STRING)
    private PgCompanySettlementStatus pgCompanySettlementStatus;

    // 결제 id
    @Column(length = 12, nullable = false)
    private String paymentId;

    // partner transaction id
    @Column(length = 12, nullable = true)
    private String partnerTrxId;

    // 입금 히스토리 sequence
    @Column
    private Long depositHistSeq;

    // pg사 참조아이디
    @Column(length = 100, nullable = false)
    private String pgReferenceId;

    // 해당정산 건의 로컬 통화 코드
    private CurrencyCode localCurrencyCode;

    // 정산일
    @Column(nullable = false)
    private LocalDate settlementDate;

    // 정산 대상 데이터 조회 시작일
    @Column(nullable = false)
    private LocalDateTime settlementStartDate;

    // 정산 대상 데이터 조회 종료일
    @Column(nullable = false)
    private LocalDateTime settlementEndDate;

    // usd to local 환율
    @Column(nullable = false)
    private BigDecimal usdToLocalRate;

    // 한패스에서 관리하는 로컬통화 금액
    @Column
    private BigDecimal hpsLocalAmount;

    // pg사에서 관리하는 로컬통화 금액
    @Column
    private BigDecimal pgLocalAmount;

    // 확정 로컬통화 금액
    @Column
    private BigDecimal localAmount;

    // USD 금액
    @Column
    private BigDecimal usdAmount;

    // pg사 로컬통화 수수료
    @Column
    private BigDecimal pgFeeLocalAmount;

    // USD 환산 pg사 수수료
    @Column
    private BigDecimal pgFeeUsdAmount;

    // 해당 데이터가 한패스 DB에 있는지 유무
    @Column(nullable = false)
    private boolean isInHpsReport;

    // 해당 데이터가 pg사 레포트에 있는지 유무
    @Column(nullable = false)
    private boolean isInPgReport;

    // 한패스 데이터와 pg사 데이터 일치 유무
    @Column(nullable = false)
    private boolean isMatchedAmount;

    // 수정일
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;

    // 수정자
    @Column(length = 50)
    private String modId;

    // 신청일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    /**
     * create HanpassPgSettlement (llp)
     * @param payment
     * @param llpFx
     * @param settlementDate
     * @param settlementBeginTime
     * @param settlementEndTime
     * @return
     */
    public static HanpassPgSettlement createHanpassPgSettlement(
            Payment payment,
            LlpFx llpFx,
            LocalDate settlementDate,
            LocalDateTime settlementBeginTime,
            LocalDateTime settlementEndTime) {
        Estimate estimate = payment.getEstimate();
        Student student = payment.getStudent();
        DepositAccount depositAccount = payment.getDepositAccount();
        return HanpassPgSettlement.builder()
                .paymentRequestDate(payment.getRegDate())
                .paymentCompleteDate(payment.getPaymentCompleteDate())
                .schoolName(student.getSchoolName())
                .partnerMerchantType(payment.getPartnerMerchantType())
                .paymentStatus(payment.getPaymentStatus())
                .depositStatus(depositAccount.getDepositStatus())
                .pgCompanyType(estimate.getPgCompanyType())
                .pgCompanySettlementStatus(PgCompanySettlementStatus.READY)
                .paymentId(payment.getPaymentId())
                .partnerTrxId(payment.getPartnerTrxId())
                .depositHistSeq(null)
                .pgReferenceId(payment.getPgTrxId())
                .localCurrencyCode(estimate.getCurrency().getCurrencyCode())
                .settlementDate(settlementDate)
                .settlementStartDate(settlementBeginTime)
                .settlementEndDate(settlementEndTime)
                .usdToLocalRate(llpFx.getUsdToLocalFxRate())
                .hpsLocalAmount(llpFx.getFxLocalAmount())
                .pgLocalAmount(BigDecimal.ZERO)
                .localAmount(BigDecimal.ZERO)
                .usdAmount(BigDecimal.ZERO)
                .pgFeeLocalAmount(BigDecimal.ZERO)
                .isInHpsReport(true)
                .isInPgReport(false)
                .isMatchedAmount(false)
                .build();
    }

    /**
     * create HanpassPgSettlement (queenbee)
     * @param payment
     * @param depositHist
     * @param settlementDate
     * @param settlementBeginTime
     * @param settlementEndTime
     * @param usdToLocalRate
     * @return
     */
    public static HanpassPgSettlement createHanpassPgSettlement(
            Payment payment,
            DepositHist depositHist,
            LocalDate settlementDate,
            LocalDateTime settlementBeginTime,
            LocalDateTime settlementEndTime,
            BigDecimal usdToLocalRate) {
        Estimate estimate = payment.getEstimate();
        Student student = payment.getStudent();
        return HanpassPgSettlement.builder()
                .paymentRequestDate(payment.getRegDate())
                .paymentCompleteDate(payment.getPaymentCompleteDate())
                .schoolName(student.getSchoolName())
                .partnerMerchantType(payment.getPartnerMerchantType())
                .paymentStatus(payment.getPaymentStatus())
                .depositStatus(depositHist.getDepositStatus())
                .pgCompanyType(estimate.getPgCompanyType())
                .pgCompanySettlementStatus(PgCompanySettlementStatus.READY)
                .paymentId(payment.getPaymentId())
                .partnerTrxId(payment.getPartnerTrxId())
                .depositHistSeq(depositHist.getDepositHistSeq())
                .pgReferenceId(depositHist.getPgDepositId())
                .localCurrencyCode(estimate.getCurrency().getCurrencyCode())
                .settlementDate(settlementDate)
                .settlementStartDate(settlementBeginTime)
                .settlementEndDate(settlementEndTime)
                .usdToLocalRate(usdToLocalRate)
                .hpsLocalAmount(depositHist.getDepositAmount())
                .pgLocalAmount(BigDecimal.ZERO)
                .localAmount(BigDecimal.ZERO)
                .usdAmount(BigDecimal.ZERO)
                .pgFeeLocalAmount(BigDecimal.ZERO)
                .isInHpsReport(true)
                .isInPgReport(false)
                .isMatchedAmount(false)
                .build();
    }

    /**
     * update valid settlement
     * @param usdToLocalRate
     * @param pgFeeRate
     * @param queenbeeLocalAmount
     * @return
     */
    public Optional<HanpassPgSettlement> updateValidSettlement(
            BigDecimal usdToLocalRate,
            BigDecimal pgFeeRate,
            BigDecimal queenbeeLocalAmount) {
        BigDecimal usdSettlementAmount = queenbeeLocalAmount.divide(
                usdToLocalRate, new MathContext(18, RoundingMode.CEILING))
                .setScale(8, RoundingMode.CEILING);
        BigDecimal pgFeeLocalAmount = queenbeeLocalAmount.multiply(
                pgFeeRate, new MathContext(18, RoundingMode.CEILING))
                .setScale(8, RoundingMode.FLOOR);
        BigDecimal pgFeeUsdAmount = pgFeeLocalAmount.divide(
                usdToLocalRate, new MathContext(18, RoundingMode.CEILING))
                .setScale(8, RoundingMode.CEILING);
        this.setPgLocalAmount(queenbeeLocalAmount);
        this.setLocalAmount(queenbeeLocalAmount);
        this.setUsdAmount(usdSettlementAmount);
        this.setPgFeeLocalAmount(pgFeeLocalAmount);
        this.setPgFeeUsdAmount(pgFeeUsdAmount);
        this.setInPgReport(true);
        this.setMatchedAmount(true);
        return Optional.of(this);
    }

    /**
     * update incorrect amount settlement
     * @param pgLocalAmount
     * @return
     */
    public Optional<HanpassPgSettlement> updateIncorrectAmountSettlement(BigDecimal pgLocalAmount) {
        this.setPgLocalAmount(pgLocalAmount);
        this.setInPgReport(true);
        this.setMatchedAmount(false);
        return Optional.of(this);
    }

}
