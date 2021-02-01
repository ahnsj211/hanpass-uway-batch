package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CurrencyCode;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-02
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
@Table(name="exchange_rate", indexes = {
        @Index(name = "exchange_rate_idx1", columnList = "fromCurrencyCode, toCurrencyCode", unique = true)
})
public class ExchangeRate implements Serializable {

    // exchange_rate table pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeRateSeq;

    // 기준통화
    @Column(length = 3, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode fromCurrencyCode;

    // 대상통화
    @Column(length = 3, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode toCurrencyCode;

    // 환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal exchangeRate;

    // 수정일
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;
}
