package com.hanpass.batch.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpass.batch.xe.dto.XeResDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

/**
 * Package :: com.hanpass.batch.test
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-14
 * Description ::
 */
@SpringBootTest
public class XeRestApiTest {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hanpass.convert.url}")
    private String xeApiUrl;

    @Value("${hanpass.xe.account.id}")
    private String xeAccountId;

    @Value("${hanpass.xe.account.api.key}")
    private String xeApiKey;

    @Test
    public void getXeResDtoTest() throws Exception {
        // xe api 인증정보
        String xeAuthInfo = new String(
                Base64.getEncoder().encode(
                        String.format("%s:%s", xeAccountId, xeApiKey).getBytes()
                )
        );

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(xeApiUrl)
                .queryParam("from", "USD")
                .queryParam("to", "JPY")
                .queryParam("amount", 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", String.format("Basic %s", xeAuthInfo));

        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<XeResDto> response =
                restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, XeResDto.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody()));

    }
}
