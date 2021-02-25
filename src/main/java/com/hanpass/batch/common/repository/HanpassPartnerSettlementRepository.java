package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.HanpassPartnerSettlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.batch.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2021/02/05
 * Description ::
 */
public interface HanpassPartnerSettlementRepository extends JpaRepository<HanpassPartnerSettlement, Long> {
}
