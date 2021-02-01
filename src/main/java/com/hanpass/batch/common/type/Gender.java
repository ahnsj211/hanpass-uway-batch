package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-11
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String text;
}
