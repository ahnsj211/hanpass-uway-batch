package com.hanpass.batch.xe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * xe.com의 Convert From Exchange Ragtes API 응답 객체에 포함 되는 to currency 정보
 * (입력 된 base currency의 amount에 해당 하는  term currency의 환율을 조회 한다.)
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
public class XeToCurrencyDto {

    @JsonProperty(required = true, value = "quotecurrency")
    private String toCurrencyCode;

    @JsonProperty(required = true, value = "mid")
    private BigDecimal rate;
}
