package com.hanpass.batch.xe.controller;

import com.hanpass.batch.common.dto.ApiResponse;
import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.xe.dto.XeExchangeRateDto;
import com.hanpass.batch.xe.service.XeExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Package :: com.hanpass.batch.xe.controller
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-14
 * Description ::
 */
@RestController
@RequestMapping(path = "/xe")
@Validated
@RequiredArgsConstructor
public class XeExchangeRateController {

    private final XeExchangeRateService xeExchangeRateService;

    /**
     * USD to toCurrencyCode 에 해당하는 환율조회
     * @param toCurrencyCode
     * @return
     */
    @GetMapping(path = "/rate/{toCurrencyCode}")
    public ApiResponse<XeExchangeRateDto> getExchangeRateDto(@PathVariable(name = "toCurrencyCode") CurrencyCode toCurrencyCode) {
        return xeExchangeRateService.getXeExchangeRateDto(toCurrencyCode);
    }
}
