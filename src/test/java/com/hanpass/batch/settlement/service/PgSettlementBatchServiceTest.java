package com.hanpass.batch.settlement.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Package :: com.hanpass.batch.settlement.service
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/29
 * Description ::
 */
@ActiveProfiles("local")
@SpringBootTest
class PgSettlementBatchServiceTest {

    @Autowired
    private PgSettlementBatchService pgSettlementBatchService;

    @Test
    public void queenbeeSettlement() throws Exception {
        pgSettlementBatchService.queenbeeSettlement();
    }
}