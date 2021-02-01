package com.hanpass.batch.common.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-11
 * Description :: terms entity
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
@Table(name="terms", indexes = {
        @Index(name = "terms_idx1", columnList = "titleEn"),
        @Index(name = "terms_idx2", columnList = "noticeDate"),
        @Index(name = "terms_idx3", columnList = "isMandatory"),
        @Index(name = "terms_idx4", columnList = "deleted"),
        @Index(name = "terms_idx5", columnList = "titleKr")
})
public class Terms implements Serializable {

    // terms pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long termsSeq;

    // 영문 약관제목
    @Column(length = 50, nullable = false)
    private String titleEn;

    // 영문 약관내용
    @Column(nullable = false)
    @Lob
    private String contentEn;

    // 국문 약관제목
    @Column(length = 50, nullable = false)
    private String titleKr;

    // 국문 약관내용
    @Column(nullable = false)
    @Lob
    private String contentKr;

    // 고시일
    @Column(nullable = false)
    private LocalDate noticeDate;

    // 버전
    @Column(length = 10, nullable = false)
    private String version;

    // 필수약관유무
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Builder.Default
    private Boolean isMandatory = false;

    // 삭제유무
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Builder.Default
    private Boolean deleted = false;

    // 등록일
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime regDate;
}
