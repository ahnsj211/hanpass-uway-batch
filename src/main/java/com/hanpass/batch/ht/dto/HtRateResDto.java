package com.hanpass.batch.ht.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class HtRateResDto {

    // 환율조회 기준일
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyyMMdd")
    private LocalDate baseDate;

    // 고시일자
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyyMMdd")
    private LocalDate noticeDate;

    // 고시시간
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="HHmmss")
    private LocalTime noticeTime;

    // 고시회차
    private String noticeCount;

    // 만료시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDateTime;

    // 환율정보 목록
    @JsonProperty(value = "exchangeRateInfos")
    private List<HtRateInfoDto> htRateInfoDtos;
}
