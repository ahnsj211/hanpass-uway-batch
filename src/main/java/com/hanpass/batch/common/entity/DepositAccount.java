package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.error.ServiceError;
import com.hanpass.batch.common.type.DepositStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-28
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
@Table(name="deposit_account", indexes = {
        @Index(name = "deposit_account_idx1", columnList = "depositBankName, depositAccountNum, depositStatus"),
        @Index(name = "deposit_account_idx2", columnList = "depositStatus, expiredDate")
})
public class DepositAccount implements Serializable {

    // deposit_account pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long depositAccountSeq;

    // 입금 은행코드
    @Column(length = 30)
    private String depositBankCode;

    // 입금 은행명
    @Column(length = 100, nullable = false)
    private String depositBankName;

    // 입금 은행 지점명
    @Column(length = 50)
    private String depositBankBranchName;

    // 입금 계좌번호
    @Column(length = 30, nullable = false)
    private String depositAccountNum;

    // 입금 상태
    @Column(length = 30, nullable = false)
    @Enumerated(STRING)
    private DepositStatus depositStatus;

    // 입금일
    @Column
    private LocalDateTime depositDate;

    // 입금일(현지시간)
    @Column
    private LocalDateTime depositLocalDate;

    // 결제 만료일(가상계좌 입금 만료일)
    @Column
    private LocalDateTime expiredDate;

    // 수정일
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;

    // 신청일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
