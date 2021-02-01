package com.hanpass.batch.common.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-20
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
@Table(name="refund_account", indexes = {
        @Index(name = "refund_account_idx1", columnList = "refundAccountNo"),
        @Index(name = "refund_account_idx2", columnList = "refundAccountOwner")
})
public class RefundAccount {

    // refund_account pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long refundAccountSeq;

    // RefundAccount(n) : payment(1)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="payment_id")
    private Payment payment;

    // 환불 은행 코드
    @Column(length = 10)
    private String refundBankCode;

    // 환불 은행명
    @Column(length = 100)
    private String refundBankName;

    // 환불 계좌번호
    @Column(length = 30, nullable = false)
    private String refundAccountNo;

    // 계좌주
    @Column(length = 50, nullable = false)
    private String refundAccountOwner;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
