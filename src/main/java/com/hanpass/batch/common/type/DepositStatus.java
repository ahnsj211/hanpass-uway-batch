package com.hanpass.batch.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-28
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum DepositStatus {
    // 입금 대기
    READY(PaymentStatus.REQUEST, PaymentDetailStatus.VIRTUAL_ACCOUNT_OPENED),

    // 부분입금
    PARTIAL(PaymentStatus.PROGRESS, PaymentDetailStatus.DEPOSIT_PARTIAL),

    // 초과입금
    EXCEEDED(PaymentStatus.COMPLETE, PaymentDetailStatus.DEPOSIT_EXCEEDED),

    // 입금완료
    COMPLETE(PaymentStatus.COMPLETE, PaymentDetailStatus.DEPOSIT_COMPLETE),

    // 입금실패
    FAIL(PaymentStatus.FAIL, PaymentDetailStatus.DEPOSIT_FAIL);

    private final PaymentStatus paymentStatus;
    private final PaymentDetailStatus paymentDetailStatus;

    /**
     * 입금액과 입금예정액을 비교하여 입금타입 맵핑
     * @param totalDepositAmount
     * @param estimateAmount
     * @return
     */
    public static DepositStatus getDepositStatus(BigDecimal totalDepositAmount, BigDecimal estimateAmount) {
        DepositStatus depositStatus;
        switch (totalDepositAmount.compareTo(estimateAmount)) {
            // 부분입금
            case -1:
                depositStatus = DepositStatus.PARTIAL;
                break;
            // 초과입금
            case 1:
                depositStatus = DepositStatus.EXCEEDED;
                break;
            // 정액입금
            default:
                depositStatus = DepositStatus.COMPLETE;
        }
        return depositStatus;
    }
}
