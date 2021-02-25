package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Package :: com.hanpass.batch.common.type
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-12
 * Description ::
 *
 */
@Slf4j
@AllArgsConstructor
@Getter
public enum PgCompanyType {
    QUEENBEE(2, new BigDecimal(0.004)),
    INSTAMO(1, new BigDecimal(0.004)),
    LIAN_LIAN_PAY(1, new BigDecimal(0.005)),
    INNOPAY(1, new BigDecimal(0.004)),
    TRANGLO(1, new BigDecimal(0.004));

    private long settlementTerm;
    private BigDecimal pgFeeRate;
}