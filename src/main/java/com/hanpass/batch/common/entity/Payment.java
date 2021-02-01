package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.converter.TermsAgreeConverter;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.type.*;
import com.hanpass.batch.common.vo.TermsAgreeVo;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-11
 * Description :: payment entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"student", "estimate", "depositAccount", "payer"})
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="payment", indexes = {
        @Index(name = "payment_idx1", columnList = "partnerTrxId, partnerMerchantType"),
        @Index(name = "payment_idx2", columnList = "pgTrxId"),
        @Index(name = "payment_idx3", columnList = "paymentStatus"),
        @Index(name = "payment_idx4", columnList = "regDate")
})
public class Payment implements Serializable {

    // 결제 ID
    @Id
    @GenericGenerator(name = "PAYMENT_ID_GENERATOR", strategy = "com.hanpass.batch.common.generator.PaymentIdGenerator")
    @GeneratedValue(generator = "PAYMENT_ID_GENERATOR")
    @Column(length = 12)
    private String paymentId;

    // 파트너 트랜잭션 ID
    @Column(length = 50, nullable = false)
    private String partnerTrxId;

    // 파트너 상품 ID (등록금, 학자금 등)
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PartnerMerchantType partnerMerchantType;

    // 학생정보, payment(1) : student(1)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_seq")
    private Student student;

    // PG사 트랜잭션 ID
    @Column(length = 50)
    private String pgTrxId;

    // 결제 상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;

    // 결제 상세 상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PaymentDetailStatus paymentDetailStatus;

    // 파트너의 결제상태 콜백 url
    @Column(length = 300, nullable = false)
    private String paymentStatusCallbackUrl;

    // 증명서 파일 저장 경로
    @Column(length = 250)
    private String certificateFilePath;

    // 결제견적, payment(1) : estimate(1)
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "estimate_seq")
    private Estimate estimate;

    // 입금계좌, payment(1) : deposit_account(1)
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "deposit_account_seq")
    private DepositAccount depositAccount;

    // 납부자 정보, payment(n) : Payer(1)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "payer_seq")
    private Payer payer;

    // 납부 시작일
    @Column(nullable = false)
    private LocalDateTime paymentStartDate;

    // 납부 마감일
    @Column(nullable = false)
    private LocalDateTime paymentEndDate;

    // 결제 만료일
    @Column(nullable = false)
    private LocalDateTime paymentExpiredDate;

    // 결제완료일
    @Column
    private LocalDateTime completeDate;

    // pg사 정산, payment(1) : pgSettlementFx(1)
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "pg_settlement_fx_seq")
    private PgSettlementFx pgSettlementFx;

    // 약관동의 정보
    @Column(name = "terms_agree_info")
    @Lob
    @Convert(converter = TermsAgreeConverter.class)
    private TermsAgreeVo termsAgreeVo;

    // 수정일
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;

    // 신청일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
