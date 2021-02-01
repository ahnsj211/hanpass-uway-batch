package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.Currency;
import com.hanpass.batch.common.type.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-15
 * Description :: currency repository
 */
public interface CurrencyRepository extends JpaRepository<Currency, CurrencyCode> {

    /**
     * 서비스 통화목록 조회
     * @return
     */
    List<Currency> findByIsServiceCurrencyTrue();

    /**
     * 입력통화코드에 해당하는 서비스 통화목록 조회
     * @param currencyCodes
     * @return
     */
    List<Currency> findByCurrencyCodeInAndIsServiceCurrencyTrue(List<CurrencyCode> currencyCodes);

    /**
     * 입력통화코드에 해당하는 서비스 통화목록 조회
     * @param currencyCodes
     * @return
     */
    List<Currency> findByCurrencyCodeIn(List<CurrencyCode> currencyCodes);
}
