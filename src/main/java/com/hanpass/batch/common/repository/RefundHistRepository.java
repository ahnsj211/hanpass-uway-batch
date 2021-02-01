package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.entity.RefundHist;
import com.hanpass.batch.common.type.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.uway.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/09/01
 * Description ::
 */
public interface RefundHistRepository extends JpaRepository<RefundHist, Long> {

    /**
     * 결제에 해당하는 환불 히스토리 목록 조회
     * @param payment
     * @return
     */
    List<RefundHist> findByPayment(Payment payment);

    /**
     * 환불상태로 환불 히스토리 목록 조회
     * @param refundStatus
     * @return
     */
    List<RefundHist> findByRefundStatus(RefundStatus refundStatus);
}
