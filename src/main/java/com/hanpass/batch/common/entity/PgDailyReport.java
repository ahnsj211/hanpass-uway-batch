package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CurrencyCode;
import com.hanpass.batch.common.type.PgCompanyType;
import com.hanpass.batch.common.type.PgDailyReportDataType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

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
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="pg_daily_report", indexes = {
        @Index(name = "pg_daily_report_idx1", columnList = "pgCompanyType, reportDate"),
        @Index(name = "pg_daily_report_idx2", columnList = "pgReportId", unique = true)
})
public class PgDailyReport {

    // pg daily report sequence
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pgDailyReportSeq;

    // pg사 유형
    @Column(length = 30, nullable = false)
    @Enumerated(value = STRING)
    private PgCompanyType pgCompanyType;

    // pg사 레포트 id
    @Column(length = 100)
    private String pgReportId;

    // 레포트 데이터 타입(입금에 대한 레포트 or 결제에 대한 레포트)
    @Column(length = 30, nullable = false)
    @Enumerated(value = STRING)
    private PgDailyReportDataType pgDailyReportDataType;

    // 레포트 시작시간
    @Column(nullable = false)
    private LocalDateTime reportBeginTime;

    // 레포트 종료시간
    @Column(nullable = false)
    private LocalDateTime reportEndTime;

    // 레포트 데이터 전체 갯수
    @Column(nullable = false)
    private int totalCount;

    // 레포트 데이터 전체 금액 통화코드
    @Column(length = 30, nullable = false)
    @Enumerated(value = STRING)
    private CurrencyCode totalAmountCurrencyCode;

    // 레포트 데이터 전체 금액
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal totalAmount;

    // 레포트 일
    @Column(nullable = false)
    private LocalDate reportDate;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
