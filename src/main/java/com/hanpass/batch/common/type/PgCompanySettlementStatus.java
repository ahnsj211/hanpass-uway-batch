package com.hanpass.batch.common.type;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/28
 * Description ::
 */
public enum PgCompanySettlementStatus {
    // 정산대기 (배치에서 pg사 정산데이터 생성 후 관리자 검증 대기)
    READY,

    // 검증완료 (관리자가 정산데이터 검증 완료)
    ADMIN_VERIFIED,

    // 정산완료 (PG사에서 한패스 모계좌에 입금 후 관리자가 정산완료 처리)
    COMPLETE;
}
