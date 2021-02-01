package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.Payer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-01
 * Description ::
 */
public interface PayerRepository extends JpaRepository<Payer, Long> {

    /**
     * 이름, 신분증 번호로 납부자 조회
     * @param nameInCountry
     * @param identifierNum
     * @return
     */
    Optional<Payer> findDistinctTopByNameInCountryAndIdentifierNum(String nameInCountry, String identifierNum);
}
