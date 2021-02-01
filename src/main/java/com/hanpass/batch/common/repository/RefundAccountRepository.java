package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.entity.RefundAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.uway.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/09/01
 * Description ::
 */
public interface RefundAccountRepository extends JpaRepository<RefundAccount, Long> {

    /**
     * 결제에 해당하는 환불계좌 목록 조회
     * @param payment
     * @return
     */
    List<RefundAccount> findByPayment(Payment payment);
}
