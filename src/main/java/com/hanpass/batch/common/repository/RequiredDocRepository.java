package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.entity.RequiredDoc;
import com.hanpass.batch.common.type.AuditStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-08
 * Description ::
 */
public interface RequiredDocRepository extends JpaRepository<RequiredDoc, Long> {

    /**
     * 결제에 해당하는 필수문서 조회
     * @param payment
     * @return
     */
    List<RequiredDoc> findByPayment(Payment payment);

    /**
     * 결제, 문서심사상태로 필수문서 조회
     * @param payment
     * @param auditStatus
     * @return
     */
    List<RequiredDoc> findByPaymentAndAuditStatus(Payment payment, AuditStatus auditStatus);
}
