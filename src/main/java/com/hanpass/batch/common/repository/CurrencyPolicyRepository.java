package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.CurrencyPolicy;
import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.common.type.PgCompanyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Package :: com.hanpass.uway.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/08/31
 * Description ::
 */
public interface CurrencyPolicyRepository extends JpaRepository<CurrencyPolicy, Long> {

    /**
     * pg사유형 및 통화코드로 통화정책 조회
     * @param pgCompanyType
     * @param currencyCode
     * @return
     */
    Optional<CurrencyPolicy> findByPgCompanyTypeAndCurrencyCode(
            PgCompanyType pgCompanyType, CurrencyCode currencyCode);
}
