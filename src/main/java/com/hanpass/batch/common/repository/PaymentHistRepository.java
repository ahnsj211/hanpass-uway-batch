package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.entity.PaymentHist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-15
 * Description :: payment history repository
 */
public interface PaymentHistRepository extends JpaRepository<PaymentHist, Long> {

    /**
     * 결제정보로 결제 히스토리 조회
     * @param payment
     * @return
     */
    List<PaymentHist> findByPayment(Payment payment);

    /**
     * 파트너에 전달되지 않은 결제 상태 조회
     * @return
     */
    List<PaymentHist> findByPartnerNotifiedFalse();
}
