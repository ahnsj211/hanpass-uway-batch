package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.type.CountryCode;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-11
 * Description :: country entity
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
@Table(name="country")
public class Country implements Serializable {

    // 국가코드
    @Id
    @Column(length = 2)
    @Enumerated(STRING)
    private CountryCode countryCode;

    // 국가명(영문)
    @Column(length = 100, nullable = false)
    private String enName;

    // 국가번호
    @Column(length = 10, nullable = false)
    private String countryNo;

}
