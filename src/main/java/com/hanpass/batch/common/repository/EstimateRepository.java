package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-04
 * Description ::
 */
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
}
