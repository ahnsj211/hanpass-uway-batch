package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.ExchangeRate;
import com.hanpass.batch.common.type.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-02
 * Description ::
 */
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    /**
     * fromCurrencyCode 와 toCurrencyCode로 환율 조회
     * @param fromCurrencyCode
     * @param toCurrencyCode
     * @return
     */
    Optional<ExchangeRate> findByFromCurrencyCodeAndToCurrencyCode(CurrencyCode fromCurrencyCode, CurrencyCode toCurrencyCode);

    /**
     * toCurrencyCode로 환율 조회
     * @param toCurrencyCode
     * @return
     */
    Optional<ExchangeRate> findTopByToCurrencyCodeOrderByExchangeRateSeqDesc(CurrencyCode toCurrencyCode);
}
