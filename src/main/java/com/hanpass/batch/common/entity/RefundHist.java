package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.RefundStatus;
import com.hanpass.batch.common.type.RefundType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020/09/01
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
@Table(name="refund_hist", indexes = {
        @Index(name = "refund_hist_idx1", columnList = "refundType"),
        @Index(name = "refund_hist_idx2", columnList = "refundStatus"),
        @Index(name = "refund_hist_idx3", columnList = "refundRequestDate")
})
public class RefundHist {

    // refund_account pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long refundHistSeq;

    // RefundHist(n) : RefundAccount(1)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "refund_account_seq", nullable = false)
    private RefundAccount refundAccount;

    // RefundHist(n) : payment(1)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="payment_id")
    private Payment payment;

    // 환불 유형
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private RefundType refundType;

    // 환불 상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private RefundStatus refundStatus;

    // 환불 원금
    @Column(nullable = false)
    private BigDecimal refundPrincipalAmount;

    // 환불 수수료(한패스)
    @Column(nullable = false)
    private BigDecimal refundHpFee;

    // 환불 수수료(pg사)
    @Column(nullable = false)
    private BigDecimal refundPgFee;

    // 환불 수수료가 차감된 환불 금액
    @Column(nullable = false)
    private BigDecimal refundAmount;

    // 환불 만료일
    @Column
    private LocalDateTime refundExpiredDate;

    // 환불 요청일
    @Column(nullable = false)
    private LocalDateTime refundRequestDate;

    // 환불 완료일
    @Column
    private LocalDateTime refundCompleteDate;

}
