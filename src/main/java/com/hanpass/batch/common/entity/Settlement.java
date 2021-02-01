package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.PgCompanyType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.EnumType.STRING;

/**
 * Package :: com.hanpass.batch.settlement
 * Developer :: Ahn Seong-jin
 * Date :: 2020/12/02
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
@Table(name="settlement", indexes = {
        @Index(name = "settlement_idx1", columnList = "pgCompanyType, settlementDate"),
        @Index(name = "settlement_idx2", columnList = "settlementDate")
})
public class Settlement {

    // 시퀀스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settlementSeq;

    // pg사 유형
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PgCompanyType pgCompanyType;

    // 결제 ID
    @Column(length = 12, nullable = false)
    private String paymentId;

    // 입금 시퀀스
    @Column
    private Long depositHistSeq;

    // 정산일
    @Column(nullable = false)
    private LocalDate settlementDate;

    // 송금예정일
    @Column(nullable = false)
    private LocalDate remittanceDate;

    // USD to Local 정산환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal settlementUsdToLocalRate;

    // 정산금액 (Local 통화)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal settlementLocalAmount;

    // 정산금액 (USD)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal settlementUsdAmount;

    // PG사 수수료 (Local 통화)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal pgFeeLocalAmount;

    // PG사 수수료 (USD)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal pgFeeUsdAmount;

    // 한패스 수수료
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal hanpassFeeAmount;

    // 파트너 수수료
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal partnerFeeAmount;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;


}
