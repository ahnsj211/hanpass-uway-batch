package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-15
 * Description :: country repository
 */
public interface CountryRepository extends JpaRepository<Country, String> {
}
