package com.hanpass.batch.settlement.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Package :: com.hanpass.batch.settlement.service
 * Developer :: Ahn Seong-jin
 * Date :: 2021/02/05
 * Description ::
 */
@ActiveProfiles("local")
@SpringBootTest
@Transactional
@Rollback(false)
class PartnerSettlementBatchServiceTest {

    @Autowired
    private PartnerSettlementBatchService partnerSettlementBatchService;

    @Test
    public void partnerSettlementTest() throws Exception {
        partnerSettlementBatchService.partnerSettlement();
    }
}