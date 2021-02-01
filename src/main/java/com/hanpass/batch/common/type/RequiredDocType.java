package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-02
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum RequiredDocType {
    // 입학확인서
    ADMISSION_CONFIRMATION("Admission confirmation"),

    // 청구서
    INVOICE("Invoice"),

    // 학생 여권 사본
    STUDENT_PASSPORT_COPY("Copy of student's passport"),

    // 부모 여권 사본
    PARENT_PASSPORT_COPY("parent_passport_copy"),

    // 비자 스템프 사본
    VISA_STAMP_COPY("Visa stamp copy"),

    // 부모/스폰서의 여권 또는 신분증
    PARENT_SPONSORS_PASSPORT_OR_ID_CARD("Parent/Sponsor's passport or ID card"),

    // 가족관계증명서
    FAMILY_CERTIFICATE("Family certificate");

    private final String text;

    /**
     * name으로 RequiredDocType 조회
     * @param name
     * @return
     */
    public static Optional<RequiredDocType> findRequiredDocType(String name) {
        return Arrays.asList(RequiredDocType.values()).stream()
                .filter(doc -> doc.name().equals(name))
                .findAny();
    }
}
