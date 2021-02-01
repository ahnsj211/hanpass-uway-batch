package com.hanpass.batch.common.type;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-20
 * Description ::
 */
public enum RefundStatus {
    // 대기
    READY,

    // 요청
    REQUEST,

    // 진행중
    PROGRESS,

    // 완료
    COMPLETE,

    // 실패
    FAIL;
}
