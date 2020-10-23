package com.hanpass.batch.xe.service;

import com.hanpass.batch.common.dto.ApiResponse;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.xe.dto.XeExchangeRateDto;
import com.hanpass.batch.xe.dto.XeResDto;
import com.hanpass.batch.xe.dto.XeToCurrencyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;
import java.util.Optional;

/**
 * Package :: com.hanpass.batch.xe.service
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-14
 * Description ::
 */
@Service
@RequiredArgsConstructor
public class XeExchangeRateServiceImpl implements XeExchangeRateService {

    private final RestTemplate restTemplate;

    @Value("${hanpass.convert.url}")
    private String xeApiUrl;

    @Value("${hanpass.xe.account.id}")
    private String xeAccountId;

    @Value("${hanpass.xe.account.api.key}")
    private String xeApiKey;

    @Override
    public ApiResponse<XeExchangeRateDto> getXeExchangeRateDto(CurrencyCode toCurrencyCode) throws ServiceException {
        try {
            final String fromCurrencyCode = "USD";

            // xe api 인증정보
            String xeAuthInfo = new String(
                    Base64.getEncoder().encode(
                            String.format("%s:%s", xeAccountId, xeApiKey).getBytes()
                    )
            );

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(xeApiUrl)
                    .queryParam("from", fromCurrencyCode)
                    .queryParam("to", toCurrencyCode.name())
                    .queryParam("amount", 1);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            headers.add("Authorization", String.format("Basic %s", xeAuthInfo));

            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<XeResDto> response =
                    restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, XeResDto.class);

            XeResDto xeResDto = response.getBody();
            XeToCurrencyDto xeToCurrencyDto = Optional.ofNullable(xeResDto.getXeToCurrencyDtos())
                    .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, "toCurrency is invalid"))
                    .get(0);

            // response
            XeExchangeRateDto xeExchangeRateDto = XeExchangeRateDto.builder()
                    .exchangeRate(xeToCurrencyDto.getRate())
                    .fromCurrencyCode(fromCurrencyCode)
                    .toCurrencyCode(toCurrencyCode)
                    .build();
            return ApiResponse.<XeExchangeRateDto>builder()
                    .resultData(xeExchangeRateDto)
                    .build();
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                    e.getMessage(), e);
        }
    }
}
