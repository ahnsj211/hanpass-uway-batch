package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.common.type.PgSettlementStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

/**
 * Package :: com.hanpass.uway.settlement.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020/09/22
 * Description ::
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="pg_settlement_fx", indexes = {
        @Index(name = "pg_settlement_fx_idx1", columnList = "pgSettlementStatus, settlementDate")
})
public class PgSettlementFx {

    // pg_settlement_fx table pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pg_settlement_fx_seq;

    // 정산상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PgSettlementStatus pgSettlementStatus;

    // 기준통화
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode fromCurrencyCode;

    // 대상통화
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode toCurrencyCode;

    // 정산환율
    @Column(precision = 18, scale = 8)
    private BigDecimal settlementRate;

    // 정산 요청 금액(local 통화)
    @Column(precision = 18, scale = 8)
    private BigDecimal settlementReqLocalAmount;

    // 정산금액
    @Column(precision = 18, scale = 8)
    private BigDecimal settlementAmount;

    // 정산일
    @Column(nullable = false)
    private LocalDate settlementDate;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
