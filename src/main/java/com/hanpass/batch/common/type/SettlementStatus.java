package com.hanpass.batch.common.type;

/**
 * Package :: com.hanpass.batch.common.type
 * Developer :: Ahn Seong-jin
 * Date :: 2021/02/03
 * Description ::
 */
public enum SettlementStatus {
    // 대기
    READY,

    // PG 정산 진행 중
    PROGRESS_PG_SETTLEMENT,

    // PG 정산 완료
    COMPLETE_PG_SETTLEMENT,

    // PG사 정산 실패
    FAIL_PG_SETTLEMENT,

    // 파트너 정산 진행 중
    PROGRESS_PARTNER_SETTLEMENT,

    // 파트너 정산 완료
    COMPLETE_PARTNER_SETTLEMENT;
}
