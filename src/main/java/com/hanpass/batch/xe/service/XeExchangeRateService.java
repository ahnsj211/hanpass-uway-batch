package com.hanpass.batch.xe.service;

import com.hanpass.batch.common.dto.ApiResponse;
import com.hanpass.batch.common.error.ServiceException;
import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.xe.dto.XeExchangeRateDto;

/**
 * Package :: com.hanpass.batch.xe.service
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-14
 * Description ::
 */
public interface XeExchangeRateService {

    /**
     * USD to toCurrencyCode 에 해당하는 환율조회
     * @param toCurrencyCode
     * @return
     * @throws ServiceException
     */
    ApiResponse<XeExchangeRateDto> getXeExchangeRateDto(CurrencyCode toCurrencyCode) throws ServiceException;
}
