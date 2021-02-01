package com.hanpass.batch.common.type;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-20
 * Description ::
 */
public enum PaymentDetailStatus {
    /**가상계좌*/
    VIRTUAL_ACCOUNT_OPEN_REQUEST,   // 가상계좌 생성 요청
    VIRTUAL_ACCOUNT_OPEN_FAIL,      // 가상계좌 생성 실패
    VIRTUAL_ACCOUNT_OPENED,         // 가상계좌 생성 완료

    /**입금*/
    DEPOSIT_PARTIAL,                // 부분입금
    DEPOSIT_EXCEEDED,               // 초과입금
    DEPOSIT_COMPLETE,               // 정액입금
    DEPOSIT_FAIL,                   // 입금실패
    DEPOSIT_EXPIRED,                // 입금기한 만료

    /**심사*/
    AUDITING_PAYER,                 // 납부자 정보 심사중
    AUDITING_COMPLETE_PAYER,        // 납부자 정보 심사완료
    AUDITING_REJECT_PAYER,          // 납부자 정보 심사거절
    AUDITING_REQUIRED_DOC,          // 첨부파일 심사중
    AUDITING_COMPLETE_REQUIRED_DOC, // 첨부파일 심사완료
    AUDITING_REJECT_REQUIRED_DOC,   // 첨부파일 심사거절

    /**실패*/
    FAIL_USER_CANCEL,               // 사용자 취소
    FAIL_FOREIGN_PG,                // 해외 PG사에서 결제 실패 처리

    /**완료*/
    COMPLETE_PAYMENT;               // 결제완료
}
