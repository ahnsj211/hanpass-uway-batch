package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.DepositAccount;
import com.hanpass.batch.common.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-15
 * Description :: payment repository
 */
public interface PaymentRepository extends JpaRepository<Payment, String> {

    /**
     * find Payment By DepositAccount
     * @param depositAccount
     * @return
     */
    Optional<Payment> findByDepositAccount(DepositAccount depositAccount);
}
