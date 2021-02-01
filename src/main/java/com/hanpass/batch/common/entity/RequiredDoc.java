package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.AuditStatus;
import com.hanpass.batch.common.type.RequiredDocType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.bal.data.entity
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-08
 * Description ::
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="required_doc", indexes = {
        @Index(name = "required_doc_idx1", columnList = "payment_id, requiredDocType", unique = true)
})
public class RequiredDoc {

    // required_doc pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long requiredDocSeq;

    // requiredDoc(n) : payment(1)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="payment_id")
    private Payment payment;

    // 문서 유형
    @Column(length = 50, nullable = false)
    @Enumerated(STRING)
    private RequiredDocType requiredDocType;

    // 문서 심사 상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private AuditStatus auditStatus;

    // 문서 거절 사유
    @Column(columnDefinition = "text")
    private String rejectReason;

    // 문서 저장 경로
    @Column(length = 250)
    private String requiredDocPath;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    /**
     * 필수문서 생성
     * @param payment
     * @param requiredDocType
     * @param requiredDocPath
     * @return
     */
    public static RequiredDoc createRequiredDoc(
            Payment payment, RequiredDocType requiredDocType, String requiredDocPath) {
        return RequiredDoc.builder()
                .requiredDocSeq(null)
                .payment(payment)
                .requiredDocType(requiredDocType)
                .auditStatus(AuditStatus.AUDITING)
                .requiredDocPath(requiredDocPath)
                .regDate(null)
                .build();
    }

}
