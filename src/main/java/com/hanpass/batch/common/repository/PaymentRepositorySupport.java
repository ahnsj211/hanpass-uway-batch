package com.hanpass.batch.common.repository;

import com.hanpass.batch.common.entity.Payment;
import com.hanpass.batch.common.entity.QEstimate;
import com.hanpass.batch.common.entity.QPayment;
import com.hanpass.batch.common.type.PaymentStatus;
import com.hanpass.batch.common.type.PgCompanyType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.hanpass.batch.common.entity.QEstimate.*;
import static com.hanpass.batch.common.entity.QPayment.payment;

/**
 * Package :: com.hanpass.uway.common.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020/09/07
 * Description ::
 */
@Repository
public class PaymentRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public PaymentRepositorySupport(JPAQueryFactory queryFactory) {
        super(Payment.class);
        this.queryFactory = queryFactory;
    }

    /**
     * 정산내역 조회
     * @param pgCompanyType
     * @param paymentStatus
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Payment> findSettlementList(
            PgCompanyType pgCompanyType, PaymentStatus paymentStatus, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory.selectFrom(payment)
                .innerJoin(estimate).on(payment.estimate.eq(estimate))
                .where(estimate.pgCompanyType.eq(pgCompanyType),
                        payment.paymentStatus.eq(paymentStatus),
                        payment.completeDate.between(startDate, endDate)
                ).fetch();
    }

}
