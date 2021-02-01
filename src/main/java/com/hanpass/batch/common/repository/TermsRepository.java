package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-15
 * Description :: terms repository
 */
public interface TermsRepository extends JpaRepository<Terms, Long> {

    /**
     * 삭제되지 않은 약관 조회
     * @return
     */
    List<Terms> findByDeletedFalse();
}
