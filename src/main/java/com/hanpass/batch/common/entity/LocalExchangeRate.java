package com.hanpass.batch.common.entity;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-09
 * Description ::
 */
/*@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="local_exchange_rate", indexes = {
        @Index(name = "local_exchange_rate_idx1", columnList = "local_exchange_rate_seq")
})*/
public class LocalExchangeRate {

    // local_exchange_rate table pk
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long localExchangeRateSeq;

    // 해외 PG사
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private PgCompanyType pgCompanyType;

    // 기준통화
    @Column(length = 3, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode fromCurrencyCode;

    // 대상통화
    @Column(length = 3, nullable = false)
    @Enumerated(STRING)
    private CurrencyCode toCurrencyCode;

    // 환율
    @Column(precision = 18, scale = 8, nullable = false)
    private BigDecimal exchangeRate;

    // 수정일
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;*/

}
