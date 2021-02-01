package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Package :: com.hanpass.uway.service
 * Developer :: Ahn Seong-jin
 * Date :: 2020/07/31
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum AuditType {

    // 납부자 심사
    PAYER,

    // 필수서류 심사
    REQUIRED_DOC;
}
