package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.xe.dto.XeResDto;
import com.hanpass.batch.xe.dto.XeToCurrencyDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Package :: com.hanpass.batch.xe.entity
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/03
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
@Table(name="xe_exchange_rate", indexes = {
        @Index(name = "xe_exchange_rate_idx1", columnList = "toCurrencyCode, timestamp"),
        @Index(name = "xe_exchange_rate_idx2", columnList = "regDate")
})
public class XeExchangeRate {

    // pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long xeExchangeRateSeq;

    // 기준통화
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyCode fromCurrencyCode;

    // 대상통화
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyCode toCurrencyCode;

    // 환율
    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal rate;

    // xe.com 환율 고시 시간
    @Column(nullable = false)
    private LocalDateTime timestamp;

    // 등록일
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    /**
     * create XeExchangeRate
     * @param xeResDto
     */
    public static XeExchangeRate createXeExchangeRate(XeResDto xeResDto) {
        XeToCurrencyDto xeToCurrencyDto = xeResDto.getXeToCurrencyDtos().get(0);
        return XeExchangeRate.builder()
                .fromCurrencyCode(CurrencyCode.USD)
                .toCurrencyCode(CurrencyCode.findCurrencyCode(xeToCurrencyDto.getToCurrencyCode()).get())
                .rate(xeToCurrencyDto.getRate())
                .timestamp(xeResDto.getTimestamp())
                .build();
    }

}
