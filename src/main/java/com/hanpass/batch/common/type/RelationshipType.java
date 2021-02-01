package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Package :: com.hanpass.uway.common.dto
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-11
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum RelationshipType {
    SELF("self"),
    FAMILY("family"),
    IMMEDIATE_FAMILY("immediate family"),
    OTHER_RELATIVES("other relatives"),
    FRIEND("friend"),
    OTHERS("others");

    private final String text;

}
