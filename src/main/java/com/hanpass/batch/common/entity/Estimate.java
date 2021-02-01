package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.PaymentOption;
import com.hanpass.batch.common.type.PgCompanyType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-04
 * Description ::
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"currency"})
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="estimate", indexes = {
        @Index(name = "estimate_idx1", columnList = "paymentOption"),
        @Index(name = "estimate_idx2", columnList = "pgCompanyType"),
        @Index(name = "estimate_idx3", columnList = "expiredDate"),
        @Index(name = "estimate_idx4", columnList = "regDate")
})
public class Estimate implements Serializable {

    // estimate table pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateSeq;

    // 통화코드, payment(1) : currency(1)
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "currency_code")
    private Currency currency;

    // 결제수단
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PaymentOption paymentOption;

    // pg사 유형
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PgCompanyType pgCompanyType;

    // usd to krw 환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToKrwRate;

    // usd to krw 한패스 스프레드
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToKrwHpSpread;

    // usd to krw pg사 스프레드
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToKrwPgSpread;

    // usd to krw 스프레드 (한패스 + pg 사 스프레드)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToKrwSpread;

    // usd to krw 환율에 스프레드 적용된 환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToKrwSpreadRate;

    // usd to local 환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToLocalRate;

    // usd to local 한패스 스프레드
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToLocalHpSpread;

    // usd to local pg사 스프레드
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToLocalPgSpread;

    // usd to local 스프레드 (한패스 + pg사 스프레드)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToLocalSpread;

    // usd to local 에 스프레드 적용된 환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToLocalSpreadRate;

    // krw to local 환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal krwToLocalRate;

    // 결제금액 (KRW)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal krwPaymentAmount;

    // 결제금액 (USD)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdPaymentAmount;

    // 결제금액 (로컬통화)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal localPaymentAmount;

    // 한패스 수수료
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal hpKrwFee;

    // PG사 수수료
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal pgLocalFee;

    // 견적 만료일
    @Column(nullable = false, updatable = false)
    private LocalDateTime expiredDate;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;
}
