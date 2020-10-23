package com.hanpass.batch.xe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * xe.com의 Convert From Exchange Rates API 응답 객체
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
public class XeResDto {

    @JsonProperty(required = true, value = "terms")
    private String terms;

    @JsonProperty(required = true, value = "privacy")
    private String privacy;

    @JsonProperty(required = true, value = "from")
    private String fromCurrencyCode;

    @JsonProperty(required = true, value = "amount")
    private BigDecimal amount;

    @JsonProperty(required = true, value = "timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "Asia/Seoul")
    private LocalDateTime timestamp;

    @JsonProperty(required = true, value = "to")
    private List<XeToCurrencyDto> xeToCurrencyDtos;

}
