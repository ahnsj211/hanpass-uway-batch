package com.hanpass.batch.xe.service;

import com.hanpass.batch.xe.dto.XeResDto;
import com.hanpass.batch.common.entity.XeExchangeRate;
import com.hanpass.batch.common.repository.XeExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

/**
 * Package :: com.hanpass.batch.xe.service
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/03
 * Description ::
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class XeExchangeRateBatchService {

    @Value("${hanpass.convert.url}")
    private String xeApiUrl;

    @Value("${hanpass.xe.account.id}")
    private String xeAccountId;

    @Value("${hanpass.xe.account.api.key}")
    private String xeApiKey;

    private final RestTemplate restTemplate;

    private final XeExchangeRateRepository xeExchangeRateRepository;

    @Scheduled(fixedRate = 60000)
    public void getXeExchangeRate() {

        final String fromCurrencyCode = "USD";

        // xe api 인증정보
        String xeAuthInfo = new String(
                Base64.getEncoder().encode(
                        String.format("%s:%s", xeAccountId, xeApiKey).getBytes()
                )
        );

        // TODO: 2020/12/04 : 통화 목록에 대한 환율 조회로 변경 예정
//        String toCurrencies = Arrays.stream(CurrencyCode.values())
//                .map(currencyCode -> currencyCode.name())
//                .collect(Collectors.joining(","));

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(xeApiUrl)
                .queryParam("from", fromCurrencyCode)
                .queryParam("to", "JPY")
                .queryParam("amount", 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", String.format("Basic %s", xeAuthInfo));

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<XeResDto> response =
                restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, XeResDto.class);

        XeResDto xeResDto = response.getBody();
        XeExchangeRate xeExchangeRate = XeExchangeRate.createXeExchangeRate(xeResDto);
        xeExchangeRateRepository.save(xeExchangeRate);
    }

}
