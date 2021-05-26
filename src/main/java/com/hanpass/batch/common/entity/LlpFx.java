package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.common.type.LlpFxStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

/**
 * Package :: com.hanpass.batch.common.entity
 * Developer :: Ahn Seong-jin
 * Date :: 2020/09/22
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
@Table(name="llp_fx", indexes = {
        @Index(name = "llp_fx_idx1", columnList = "llpFxStatus, fxDate")
})
public class LlpFx {

    // pg_settlement_fx table pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long llpFxSeq;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    // 정산상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private LlpFxStatus llpFxStatus;

    // 기준통화
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode fxFromCurrencyCode;

    // 대상통화
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode fxToCurrencyCode;

    // 정산환율
    @Column(precision = 18, scale = 8)
    private BigDecimal usdToLocalFxRate;

    // 정산 요청 금액(local 통화)
    @Column(precision = 18, scale = 8)
    private BigDecimal fxLocalAmount;

    // 정산된 금액(USD)
    @Column(precision = 18, scale = 8)
    private BigDecimal fxUsdAmount;

    // 정산일 (완료콜백 수신시간)
    @Column
    private LocalDateTime fxDate;

    // llp서버 정산요청일 (UTC+0)
    @Column
    private LocalDateTime llpCreateUtcDate;

    // llp서버 정산완료일 (UTC+0)
    @Column
    private LocalDateTime llpModifyUtcDate;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;
}
