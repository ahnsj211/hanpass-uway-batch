package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.DepositAccount;
import com.hanpass.batch.common.entity.DepositHist;
import com.hanpass.batch.common.type.DepositStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.uway.service
 * Developer :: Ahn Seong-jin
 * Date :: 2020/07/30
 * Description ::
 */
public interface DepositHistRepository extends JpaRepository<DepositHist, Long> {

    /**
     * 입금계좌로 입금 히스토리 조회
     * @param depositAccount
     * @return
     */
    List<DepositHist> findByDepositAccount(DepositAccount depositAccount);

    /**
     * 입금계좌,입금상태 목록으로 입금 히스토리 조회
     * @param depositAccount
     * @param depositStatuses
     * @return
     */
    List<DepositHist> findByDepositAccountAndDepositStatusIn(
            DepositAccount depositAccount, List<DepositStatus> depositStatuses);

    /**
     * 파트너에 입금통지 되지 않은 입금 목록 조회
     * @return
     */
    List<DepositHist> findByPartnerNotifiedFalse();
}
