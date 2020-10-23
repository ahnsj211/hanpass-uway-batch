package com.hanpass.batch.ht.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class HtRateInfoDto {
    // 통화코드
    private String currencyCode;

    // 최초고시환율
    private BigDecimal initNoticeExchangeRate;

    // 은행매입률
    private BigDecimal bankPurchaseRate;

    // 은행매도율
    private BigDecimal bankSellRate;

    // 제휴사고시기준율
    private BigDecimal partnerNoticeBaseRate;
}
