package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.DepositHist;
import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.entity.QDepositAccount;
import com.hanpass.batch.common.entity.QDepositHist;
import com.hanpass.batch.common.type.PaymentStatus;
import com.hanpass.batch.common.type.PgCompanyType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.hanpass.batch.common.entity.QDepositAccount.*;
import static com.hanpass.batch.common.entity.QDepositHist.*;
import static com.hanpass.batch.common.entity.QEstimate.estimate;
import static com.hanpass.batch.common.entity.QPayment.payment;

/**
 * Package :: com.hanpass.batch.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/04
 * Description ::
 */
@Repository
public class DepositHistRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public DepositHistRepositorySupport(JPAQueryFactory queryFactory) {
        super(DepositHist.class);
        this.queryFactory = queryFactory;
    }

    /**
     * 정산내역 조회
     * @param pgCompanyType
     * @param startDate
     * @param endDate
     * @return
     */
    public List<DepositHist> findSettlementsOfDepositHist(
            PgCompanyType pgCompanyType, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.selectFrom(depositHist)
                .innerJoin(depositAccount).on(depositHist.depositAccount.eq(depositAccount))
                .innerJoin(payment).on(depositAccount.eq(payment.depositAccount))
                .innerJoin(estimate).on(payment.estimate.eq(estimate))
                .where(estimate.pgCompanyType.eq(pgCompanyType),
                        depositHist.depositDate.between(startDate, endDate)
                ).fetch();
    }

}
