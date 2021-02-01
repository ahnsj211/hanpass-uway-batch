package com.hanpass.batch.common.type;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-11
 * Description ::
 */
public enum PaymentStatus {
    REQUEST,                    // 결제요청
    PROGRESS,                   // 결제진행중(부분입금)
    COMPLETE,                   // 결제완료(입금완료) (terminate)
    FAIL;                       // 한패스 시스템 or CS 취소 시킨 경우 (terminate)
}
