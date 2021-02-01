package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.HanpassPgSettlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Package :: com.hanpass.uway.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/28
 * Description ::
 */
public interface HanpassPgSettlementRepository extends JpaRepository<HanpassPgSettlement, Long> {
}
