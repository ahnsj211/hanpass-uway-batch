package com.hanpass.batch.common.type;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/28
 * Description ::
 */
public enum PgCompanySettlementStatus {
    // 정산 데이터 생성 (배치에서 pg사 정산데이터 생성)
    CREATION,

    // 정산 완료 (Operator가 정산데이터 확인 후 완료 처리)
    COMPLETE,

    // 정산 제외 (Operator가 정산데이터 확인 후 제외 처리)
    EXCLUDE;
}
