package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CurrencyCode;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2021/01/28
 * Description ::
 */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "pgDailyReport")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="queenbee_daily_report", indexes = {
        @Index(name = "queenbee_daily_report_idx1", columnList = "pg_daily_report_seq")
})
public class QueenbeeDailyReport {

    // queenbee daily report sequence
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queenbeeDailyReportSeq;

    // QueenbeeDailyReport(n) : PgDailyReport(1)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pg_daily_report_seq")
    private PgDailyReport pgDailyReport;

    // queenbee 입금 아이디
    @Column(length = 100, nullable = false)
    private String queenbeeDepositId;

    // queenbee 가상계좌 아이디
    @Column(length = 100, nullable = false)
    private String queenbeeAccountId;

    // 입금일
    @Column(nullable = false)
    private LocalDateTime depositDate;

    // 입금자 이름
    @Column(length = 100, nullable = false)
    private String senderName;

    // 입금 통화코드
    @Column(length = 30, nullable = false)
    @Enumerated(value = STRING)
    private CurrencyCode currencyCode;

    // 입금액
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal depositAmount;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;
}
