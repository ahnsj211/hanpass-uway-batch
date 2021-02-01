package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.DepositStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.controller
 * Developer :: Ahn Seong-jin
 * Date :: 2020/07/29
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
@Table(name="deposit_hist")
public class DepositHist {

    // deposit_hist pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long depositHistSeq;

    // 입금계좌, depositHist(n) : depositAccount(1)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "deposit_account_seq")
    private DepositAccount depositAccount;

    // pg사 입금 id
    @Column
    private String pgDepositId;

    // 입금액
    @Column
    private BigDecimal depositAmount;

    // 입금 상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private DepositStatus depositStatus;

    // 입금일
    @Column
    private LocalDateTime depositDate;

    // 입금일 (현지시간)
    @Column
    private LocalDateTime depositLocalDate;

    // 파트너 입금통지 유무
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean partnerNotified;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

    /**
     * get total deposit amount
     * @param depositHists
     * @return
     */
    public static BigDecimal getTotalDepositAmount(List<DepositHist> depositHists) {
        return depositHists.isEmpty() ?
                BigDecimal.ZERO :
                depositHists.stream()
                        .map(DepositHist::getDepositAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
