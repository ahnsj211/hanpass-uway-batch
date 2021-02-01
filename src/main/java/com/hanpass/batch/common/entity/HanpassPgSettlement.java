package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.PgCompanySettlementStatus;
import com.hanpass.batch.common.type.PgCompanyType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/28
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
@Table(name="hanpass_pg_settlement", indexes = {
        @Index(name = "hanpass_pg_settlement_idx1", columnList = "settlementDate"),
        @Index(name = "hanpass_pg_settlement_idx2", columnList = "settlementDate, isInHpsReport"),
        @Index(name = "hanpass_pg_settlement_idx3", columnList = "settlementDate, isInPgReport"),
        @Index(name = "hanpass_pg_settlement_idx4", columnList = "settlementDate, isMatchedAmount"),
        @Index(name = "hanpass_pg_settlement_idx5", columnList = "settlementDate, pgCompanySettlementStatus")
})
public class HanpassPgSettlement {

    // sequence
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long hanpassPgSettlementSeq;

    // pg사 유형
    @Column(length = 30, nullable = false)
    @Enumerated(value = STRING)
    private PgCompanyType pgCompanyType;

    // 정산 상태
    @Column(length = 30, nullable = false)
    @Enumerated(value = STRING)
    private PgCompanySettlementStatus pgCompanySettlementStatus;

    // 결제 id
    @Column(length = 12, nullable = false)
    private String paymentId;

    // 입금 히스토리 sequence
    @Column
    private Long depositHistSeq;

    // pg사 참조아이디
    @Column(length = 100, nullable = false)
    private String pgReferenceId;

    // 정산일
    @Column(nullable = false)
    private LocalDate settlementDate;

    // 정산 대상 데이터 조회 시작일
    @Column(nullable = false)
    private LocalDateTime settlementStartDate;

    // 정산 대상 데이터 조회 종료일
    @Column(nullable = false)
    private LocalDateTime settlementEndDate;

    // usd to local 환율
    @Column(nullable = false)
    private BigDecimal usdToLocalRate;

    // 한패스에서 관리하는 로컬통화 금액
    @Column
    private BigDecimal hpsLocalAmount;

    // pg사에서 관리하는 로컬통화 금액
    @Column
    private BigDecimal pgLocalAmount;

    // 확정 로컬통화 금액
    @Column
    private BigDecimal localAmount;

    // USD 금액
    @Column
    private BigDecimal usdAmount;

    // pg사 로컬통화 수수료
    @Column
    private BigDecimal pgFeeLocalAmount;

    // USD 환산 pg사 수수료
    @Column
    private BigDecimal pgFeeUsdAmount;

    // 해당 데이터가 한패스 DB에 있는지 유무
    @Column(nullable = false)
    private boolean isInHpsReport;

    // 해당 데이터가 pg사 레포트에 있는지 유무
    @Column(nullable = false)
    private boolean isInPgReport;

    // 한패스 데이터와 pg사 데이터 일치 유무
    @Column(nullable = false)
    private boolean isMatchedAmount;

    // 수정일
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;

    // 수정자
    @Column(length = 50)
    private String modId;

    // 신청일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
