package com.hanpass.batch.common.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * Package :: com.hanpass.uway.common.generator
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-11
 * Description ::
 */
public class PaymentIdGenerator implements IdentifierGenerator {

    /**
     * transaction id 생성
     * @param session
     * @param obj
     * @return
     * @throws HibernateException
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        LocalDate now = LocalDate.now();
        String prefix = DateTimeFormatter.ofPattern("yyMMdd").format(now);

        String query = String.format("select %s from %s where reg_date between :startDate and :endDate",
                session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        Stream<String> ids = session.createQuery(query)
                .setParameter("startDate", LocalDateTime.of(now, LocalTime.MIN))
                .setParameter("endDate", LocalDateTime.of(now, LocalTime.MAX))
                .stream();

        Long max = ids.map(o -> o.replace(prefix, ""))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);

        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(String.format("%06d", max + 1));

        return builder.toString();
    }
}
