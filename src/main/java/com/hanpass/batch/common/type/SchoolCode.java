package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
*
*   @author seongwou
*   @since 21. 2. 26. 오후 2:47
*
**/
@AllArgsConstructor
@Getter
@Deprecated
public enum SchoolCode {

    A001("A001", "Korea University", "고려대학교"),
    T999("A999", "Hanpass University", "한패스테스트");

    private final String code;
    private final String enName;
    private final String krName;

    public static SchoolCode getCode(String code) throws Exception {
        return Arrays.stream(SchoolCode.values())
                .filter(bankCode -> bankCode.getCode().equals(code))
                .findAny().orElseThrow(() -> new Exception("Invalid code"));
    }
}
