package com.hanpass.batch.ht.service;

import com.hanpass.batch.common.dto.ApiResponse;
import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.error.ServiceException;
import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.ht.dto.HtRateInfoDto;
import com.hanpass.batch.ht.dto.HtRateResDto;
import com.hanpass.batch.ht.vo.HtExchangeRateVo;
import com.hanpass.batch.xe.dto.XeResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * Package :: com.hanpass.batch.ht.service
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-14
 * Description ::
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HtExchangeRateService {

    private final RestTemplate restTemplate;

    @Value("${hanpass.ht.rate.url}")
    private String htExchangeRateUrl;

    @Value("${hanpass.api.save.ht-rate.url}")
    private String saveExchangeRateUrl;

    /**
     * 한국투자증권 환율 조회 후 api 서버에 전송
     * @throws ServiceException
     */
    @Scheduled(fixedRate = 60000) // 초 분 시 일 월 요일 년(생략가능)
    public void getHtExchangeRate() {
        try {
            /**FX 서버에서 한국투자증권 환율 조회*/
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(htExchangeRateUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json");

            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<HtRateResDto> response =
                    restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, HtRateResDto.class);

            HtRateResDto htRateResDto = response.getBody();
            HtRateInfoDto htRateInfoDto = Optional.ofNullable(htRateResDto.getHtRateInfoDtos())
                    .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, "result of exchange rate is empty"))
                    .get(0);

            /**create request object*/
            HtExchangeRateVo htExchangeRateVo = HtExchangeRateVo.builder()
                    .exchangeRate(htRateInfoDto.getPartnerNoticeBaseRate())
                    .fromCurrencyCode(
                            CurrencyCode.findCurrencyCode(htRateInfoDto.getCurrencyCode())
                                    .orElseThrow(() -> new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                                            String.format("currencyCode(%s) is not found", htRateInfoDto.getCurrencyCode())))
                    )
                    .toCurrencyCode(CurrencyCode.KRW).build();
            log.info(String.format("ht exchange rate : %s", htExchangeRateVo.toString()));

            /**send exchange rate*/
            uriBuilder = UriComponentsBuilder.fromHttpUrl(saveExchangeRateUrl);
            headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "application/json");

            HttpEntity<HtExchangeRateVo> saveRequest = new HttpEntity<HtExchangeRateVo>(htExchangeRateVo, headers);
            ResponseEntity<ApiResponse> saveResponse = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, saveRequest, ApiResponse.class);

            if(saveResponse.getBody().getHttpStatus() != HttpStatus.OK) {
                throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, "fail to save exchange rate");
            }

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR,
                    e.getMessage(), e);
        }
    }
}
