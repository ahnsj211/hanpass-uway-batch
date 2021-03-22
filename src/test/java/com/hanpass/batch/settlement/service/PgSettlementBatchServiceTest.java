package com.hanpass.batch.settlement.service;

import com.hanpass.batch.common.entity.LlpDailyReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Package :: com.hanpass.batch.settlement.service
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/29
 * Description ::
 */
@ActiveProfiles("prd")
@SpringBootTest
class PgSettlementBatchServiceTest {

    @Autowired
    private PgSettlementBatchService pgSettlementBatchService;

    @Test
    public void saveLlpReconciliationFileTest() throws Exception {
        pgSettlementBatchService.saveLlpReconciliationFile();
    }

    @Test
    public void getLlpReconciliationFileTest() throws Exception {
        pgSettlementBatchService.saveLlpReconciliationFile();
    }

    @Test
    public void queenbeeSettlement() throws Exception {
        pgSettlementBatchService.queenbeeSettlement();
    }

    @Test
    public void llpSettlementTest() throws Exception {
        pgSettlementBatchService.llpSettlement();
    }
}