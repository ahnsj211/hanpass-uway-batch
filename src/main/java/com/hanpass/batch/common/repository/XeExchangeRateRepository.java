package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.XeExchangeRate;
import com.hanpass.batch.common.type.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Package :: com.hanpass.batch.xe.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/03
 * Description ::
 */
public interface XeExchangeRateRepository extends JpaRepository<XeExchangeRate, Long> {

    /**
     * find by to currency code and timestamp
     * @param currencyCode
     * @param timestamp
     * @return
     */
    Optional<XeExchangeRate> findByToCurrencyCodeAndTimestamp(CurrencyCode currencyCode, LocalDateTime timestamp);
}
