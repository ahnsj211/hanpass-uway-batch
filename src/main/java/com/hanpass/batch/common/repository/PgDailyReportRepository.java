package com.hanpass.batch.common.repository;


import com.hanpass.batch.common.entity.PgDailyReport;
import com.hanpass.batch.common.type.PgCompanyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Package :: com.hanpass.uway.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/28
 * Description ::
 */
public interface PgDailyReportRepository extends JpaRepository<PgDailyReport, Long> {

    /**
     * find PgDailyReport By ReportDate
     * @param reportDate
     * @param pgCompanyType
     * @return
     */
    Optional<PgDailyReport> findByReportDateAndPgCompanyType(
            LocalDate reportDate, PgCompanyType pgCompanyType);
}
