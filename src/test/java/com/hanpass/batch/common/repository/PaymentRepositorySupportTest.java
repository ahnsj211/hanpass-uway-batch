package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.type.PaymentStatus;
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
class PaymentRepositorySupportTest {

    @Autowired
    private PaymentRepositorySupport paymentRepositorySupport;

    @Test
    public void findSettlementListTest() throws Exception {
        LocalDate settlementDate = LocalDate.now().minusDays(1l);
        LocalDateTime startDate = LocalDateTime.of(settlementDate, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(settlementDate, LocalTime.MAX);
        List<Payment> settlementList =
                paymentRepositorySupport.findSettlementList(PgCompanyType.LIAN_LIAN_PAY, PaymentStatus.COMPLETE, startDate, endDate);
        System.out.println(settlementList.toString());
    }
}