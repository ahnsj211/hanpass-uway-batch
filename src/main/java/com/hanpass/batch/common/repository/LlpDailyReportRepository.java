package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.LlpDailyReport;
import com.hanpass.batch.common.entity.PgDailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Package :: com.hanpass.batch.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2021/02/09
 * Description ::
 */
public interface LlpDailyReportRepository extends JpaRepository<LlpDailyReport, Long> {

    /**
     * find LlpDailyReport list by pgDailyReport
     * @param pgDailyReport
     * @return
     */
    List<LlpDailyReport> findByPgDailyReport(PgDailyReport pgDailyReport);
}
