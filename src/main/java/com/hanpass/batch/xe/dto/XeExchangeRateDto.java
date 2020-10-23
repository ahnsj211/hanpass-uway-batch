package com.hanpass.batch.xe.dto;

import com.hanpass.batch.common.type.CurrencyCode;
import lombok.*;

import java.math.BigDecimal;

/**
 * Package :: com.hanpass.batch.xe.dto
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-14
 * Description ::
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class XeExchangeRateDto {

    // 기준 통화 코드
    private String fromCurrencyCode;

    // 변환 통화 코드
    private CurrencyCode toCurrencyCode;

    // 환율
    private BigDecimal exchangeRate;
}
