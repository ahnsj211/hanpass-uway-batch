package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020/10/06
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum PayerAuditRejectFieldType {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    GENDER("gender"),
    EMAIL("email"),
    BIRTH_DATE("birthDate"),
    ADDRESS("address"),
    POST_CODE("postCode"),
    IDENTIFIER_NUM("identifierNum"),
    TELEPHONE("telephone");

    private String fieldName;

}
