package com.hanpass.batch.common.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-07-09
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
@Table(name="virtual_account", indexes = {
        @Index(name = "virtual_account_idx1", columnList = "accountNumber, bankCode, assigned, deleted", unique = true)
})
public class VirtualAccount {

    // virtual_account pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long virtualAccountSeq;

    // 가상계좌 은행코드
    @Column(length = 20, nullable = false)
    private String bankCode;

    // 가상계좌 은행명(영문)
    @Column(length = 50, nullable = false)
    private String bankNameEn;

    // 가상계좌 은행명(국문)
    @Column(length = 50, nullable = false)
    private String bankNameKr;

    // 가상계좌 번호
    @Column(length = 20, nullable = false)
    private String accountNumber;

    // 할당유무
    @Column(nullable = false)
    private Boolean assigned;

    // 할당일시
    @Column(nullable = false)
    private LocalDateTime assignedDate;

    // 삭제유무
    @Column(nullable = false)
    protected Boolean deleted;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
