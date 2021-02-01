package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.DepositHist;
import com.hanpass.batch.common.type.PgCompanyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Package :: com.hanpass.batch.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/04
 * Description ::
 */
@ActiveProfiles("local")
@SpringBootTest
class DepositHistRepositorySupportTest {

    @Autowired
    private DepositHistRepositorySupport depositHistRepositorySupport;

    @Test
    public void findSettlementListTest() throws Exception {
        LocalDate now = LocalDate.now();
        LocalDateTime startDate = LocalDateTime.of(now.minusDays(1L), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(now.minusDays(1L), LocalTime.MAX);
        List<DepositHist> depositHists = depositHistRepositorySupport.findSettlementsOfDepositHist(PgCompanyType.QUEENBEE, startDate, endDate);
        System.out.println(depositHists);
    }
}