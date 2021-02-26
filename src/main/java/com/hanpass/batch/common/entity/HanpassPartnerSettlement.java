package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CountryCode;
import com.hanpass.batch.common.type.PartnerMerchantType;
import com.hanpass.batch.common.type.SchoolCode;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.*;

/**
 * Package :: com.hanpass.batch.common.entity
 * Developer :: Ahn Seong-jin
 * Date :: 2021/02/05
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
@Table(name="hanpass_partner_settlement", indexes = {
        @Index(name = "hanpass_partner_settlement_idx1", columnList = "paymentId"),
        @Index(name = "hanpass_partner_settlement_idx2", columnList = "settlementDate"),
        @Index(name = "hanpass_partner_settlement_idx3", columnList = "paymentId, settlementDate"),
        @Index(name = "hanpass_partner_settlement_idx4", columnList = "paymentCountryCode"),
        @Index(name = "hanpass_partner_settlement_idx5", columnList = "settlementDate, paymentCountryCode")
})
public class HanpassPartnerSettlement {

    // 시퀀스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hanpassPartnerSettlementSeq;

    // 한패스 결제 id
    @Column(length = 12, nullable = false)
    private String paymentId;

    // 파트너 거래 id
    @Column(length = 50, nullable = false)
    private String partnerTrxId;

    // 청구기관
    @Column(length = 50, nullable = false)
    @Enumerated(STRING)
    private SchoolCode requestedInstitution;

    // 청구유형
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PartnerMerchantType partnerMerchantType;

    // 결제 국가
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private CountryCode paymentCountryCode;

    // 파트너 청구원금 (krw)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal partnerChargedKrwAmount;

    // pg사에서 정산받은 usd 금액(pg사 정산액 - pg사 정액 수수료 - pg사 정률 수수료)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal pgSettlementUsdAmount;

    // 파트너 정산환율(한국투자증권 환율)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal partnerSettlementRate;

    // pg사에서 정산받은 krw 금액(pg사 수수료 미포함 금액)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal pgSettlementKrwAmount;

    // 결제건에 대한 수익(pgSettlementKrwAmount - partnerChargedKrwPrincipalAmount)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal totalKrwProfitAmount;

    // 한패스 수익(krw)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal hanpassProfitKrwAmount;

    // 파트너 수익(krw)
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal partnerProfitKrwAmount;

    // 정산일
    @Column(nullable = false)
    private LocalDate settlementDate;

    // 파트너 송금예정일
    @Column(nullable = false)
    private LocalDate partnerRemittanceDate;

    // 수정자
    @Column(length = 30, nullable = false)
    private String modId;

    // 수정일
    @Column(nullable = false, updatable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;
}
