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
public enum IdType {
    ID_CARD("ID Card"),
    UNIFIED_CREDIT_CODE("Uniform social credit code"),
    NATIONAL_ID("National id"),
    WORK_PERMIT("Work permit"),
    PASSPORT("Passport"),
    MY_KAS("My KAS"),
    IDENTIFICATION_ID("Identification id"),
    SOCIAL_SECURITY("Social security"),
    RESIDENT_PERMIT("Resident permit"),
    PERMANENT_RESIDENT("Permanent resident"),
    UNHCR("UNHCR"),
    MY_TENTERA("My tentera"),
    TEMPORARY_IC("Temporary IC"),
    DRIVING_LICENSE("Driving license"),
    IKAD("IKAD"),
    DEPENDENT_PASS("Dependent pass"),
    STUDENT_PASS("Student pass"),
    VISIT_PASS("Visit pass"),
    UNIFORM_GROUPS_ID("Uniform groups id"),
    SING_PASS("Sing pass");

    private final String text;

}
