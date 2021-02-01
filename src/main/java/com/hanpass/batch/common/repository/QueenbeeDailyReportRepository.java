package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.PgDailyReport;
import com.hanpass.batch.common.entity.QueenbeeDailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.uway.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/28
 * Description ::
 */
public interface QueenbeeDailyReportRepository extends JpaRepository<QueenbeeDailyReport, Long> {

    /**
     * find QueenbeeDailyReport By PgDailyReport
     * @param pgDailyReport
     * @return
     */
    List<QueenbeeDailyReport> findByPgDailyReport(PgDailyReport pgDailyReport);
}
