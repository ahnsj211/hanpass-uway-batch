package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-01
 * Description ::
 */
public interface DepositAccountRepository extends JpaRepository<DepositAccount, Long> {
}
