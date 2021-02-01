package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.PaymentDetailStatus;
import com.hanpass.batch.common.type.PaymentStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.common.model.id
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-12
 * Description :: payment history entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "payment")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="payment_hist", indexes = {
        @Index(name = "payment_hist_idx1", columnList = "paymentStatus"),
        @Index(name = "payment_hist_idx2", columnList = "regDate"),
        @Index(name = "payment_hist_idx3", columnList = "partnerNotified")
})
public class PaymentHist implements Serializable {

    // payment_hist pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long paymentHistSeq;

    // 결제 id, PaymentHist(n) : payment(1)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // 결제 상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;

    // 결제 상세상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PaymentDetailStatus paymentDetailStatus;

    // 파트너 알림 유무
    @Column(nullable = false)
    private Boolean partnerNotified;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
