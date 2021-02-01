package com.hanpass.batch.common.type;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020/09/22
 * Description ::
 */
public enum PgSettlementStatus {

    // 정산 전
    READY,

    // 정산 요청
    REQUEST,

    // fx 정산 완료(정산 금액 까지 계산된 상태)
    COMPLETE_FX,

    // 정산완료(실제 한패스 모계좌에 돈이 입금 완료 된 상태)
    COMPLETE_SETTLEMENT,

    // 정산 실패
    FAIL,

    // 취소
    CANCEL;
}
