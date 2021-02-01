package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.VirtualAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Package :: com.hanpass.uway.service
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-09
 * Description ::
 */
public interface VirtualAccountRepository extends JpaRepository<VirtualAccount, Long> {

    /**
     * 할당되지 않은 가상계좌 조회
     * @return
     */
    Optional<VirtualAccount> findTopByAssignedFalseAndDeletedFalse();
}
