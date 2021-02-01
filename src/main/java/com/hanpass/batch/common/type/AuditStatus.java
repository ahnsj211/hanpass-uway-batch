package com.hanpass.batch.common.type;

import com.hanpass.batch.common.entity.RequiredDoc;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Package :: com.hanpass.uway.common.constant
 * Developer :: Ahn Seong-jin
 * Date :: 2020/07/31
 * Description ::
 */
@AllArgsConstructor
@Getter
public enum AuditStatus {
    AUDITING(
            Map.of(
                    AuditType.PAYER, PaymentDetailStatus.AUDITING_PAYER,
                    AuditType.REQUIRED_DOC, PaymentDetailStatus.AUDITING_REQUIRED_DOC
            )
    ),
    COMPLETE(
            Map.of(
                    AuditType.PAYER, PaymentDetailStatus.AUDITING_COMPLETE_PAYER,
                    AuditType.REQUIRED_DOC, PaymentDetailStatus.AUDITING_COMPLETE_REQUIRED_DOC
            )
    ),
    REJECT(
            Map.of(
                    AuditType.PAYER, PaymentDetailStatus.AUDITING_REJECT_PAYER,
                    AuditType.REQUIRED_DOC, PaymentDetailStatus.AUDITING_REJECT_REQUIRED_DOC
            )
    );

    private final Map<AuditType, PaymentDetailStatus> map;

    /**
     * AuditStatus에 해당하는 PaymentDetailStatus 리턴
     * @param auditType
     * @return
     */
    public PaymentDetailStatus getPaymentDetailStatus(AuditType auditType) {
        return this.map.get(auditType);
    }

    /**
     * 필수문서 심사상태 조회
     * @param requiredDocs
     * @return
     */
    public static AuditStatus getRequiredDocAuditStatus(List<RequiredDoc> requiredDocs, CountryCode countryCode) {

        // 첨부서류가 없는 국가의 경우
        if(countryCode.getRequiredDocTypes().size() == 0) {
            return AuditStatus.COMPLETE;
        }

        // 첨부문서에 대한 심사가 모두 완료된 경우 심사완료
        boolean isCompleted = !requiredDocs.stream()
                .anyMatch(doc -> doc.getAuditStatus() != AuditStatus.COMPLETE);
        if(isCompleted) {
            return AuditStatus.COMPLETE;
        }

        // 심사거절 건이 하나라도 있는경우 거절
        boolean isRejected = requiredDocs.stream()
                .anyMatch(doc -> doc.getAuditStatus() == AuditStatus.REJECT);
        if(isRejected) {
            return AuditStatus.REJECT;
        }

        // 그외 심사 진행중인 건이 하나라도 있는경우 심사중
        return AuditStatus.AUDITING;
    }
}
