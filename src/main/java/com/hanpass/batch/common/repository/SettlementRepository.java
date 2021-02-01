package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Package :: com.hanpass.batch.settlement.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/04
 * Description ::
 */
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
