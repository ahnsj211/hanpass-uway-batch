package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Package :: com.hanpass.batch.common.type
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-02
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum CurrencyCode {
    KRW,
    USD,
    CNY,
    VND,
    JPY,
    IDR,
    MNT;

    /**
     * find CurrencyCode
     * @param code
     * @return
     */
    public static Optional<CurrencyCode> findCurrencyCode(String code) {
        return Arrays.asList(CurrencyCode.values()).stream()
                .filter(c -> c.name().equals(code))
                .findAny();
    }
}
