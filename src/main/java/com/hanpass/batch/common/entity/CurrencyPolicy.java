package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.common.type.PgCompanyType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-21
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
@Table(name="currency_policy", indexes = {
        @Index(name = "currency_policy_idx1", columnList = "pgCompanyType, currencyCode", unique = true)
})
public class CurrencyPolicy {

    // currency_policy table pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currencyPolicySeq;

    // pg사 유형
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PgCompanyType pgCompanyType;

    // 통화코드
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode currencyCode;

    // usd to krw 환율에 대한 한패스 스프레드
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToKrwHpSpread;

    // usd to krw 환율에 대한 pg사 스프레드
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToKrwPgSpread;

    // usd to local 환율에 대한 한패스 스프레드
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToLocalHpSpread;

    // usd to local 환율에 대한 pg사 스프레드
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal usdToLocalPgSpread;

    // 한패스 krw 수수료
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal hpKrwFee;

    // pg사 local 통화 수수료
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal pgLocalFee;

    // 수정일
    @Column(nullable = false, updatable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;
}
