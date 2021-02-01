package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CurrencyCode;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-11
 * Description :: currency entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"country"})
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="currency")
public class Currency implements Serializable {

    // 통화코드
    @Id
    @Column(length = 3)
    @Enumerated(STRING)
    private CurrencyCode currencyCode;

    // 국가
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "country_code")
    private Country country;

    // 주요통화 유무
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isMajor;

    // 서비스 통화 유무
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isServiceCurrency;

    // 정렬순서
    @Column(nullable = false)
    private int order;
}
