package com.hanpass.batch.ht.vo;

import com.hanpass.batch.common.type.CurrencyCode;
import lombok.*;

import java.math.BigDecimal;

/**
 * Package :: com.hanpass.batch.ht.dto
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-14
 * Description ::
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class HtExchangeRateVo {

    private CurrencyCode fromCurrencyCode;

    private CurrencyCode toCurrencyCode;

    private BigDecimal exchangeRate;
}
