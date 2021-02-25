package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.DepositAccount;
import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.entity.Settlement;
import com.hanpass.batch.common.type.SettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
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

    /**
     * find Payment list By settlementStatus And pgSettlementBeginDate, pgSettlementEndDate
     * @param settlementStatus
     * @param pgSettlementBeginDate
     * @param pgSettlementEndDate
     * @return
     */
    List<Payment> findBySettlementStatusAndPgSettlementCompleteDateBetween(
            SettlementStatus settlementStatus,
            LocalDateTime pgSettlementBeginDate,
            LocalDateTime pgSettlementEndDate);
}
