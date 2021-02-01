package com.hanpass.batch.common.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpass.batch.common.converter.CipherConverter;
import com.hanpass.batch.common.type.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-05-11
 * Description :: easy transfer payer entity
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
@Table(name="payer", indexes = {
        @Index(name = "payer_idx1", columnList = "firstName, middleName, lastName"),
        @Index(name = "payer_idx2", columnList = "telephone"),
        @Index(name = "payer_idx3", columnList = "email")
})
public class Payer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payerSeq;

    // 납부자 심사상태
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private AuditStatus auditStatus;

    // 납부자 거절 사유
    @Column(columnDefinition = "text")
    private String auditRejectInfo;

    // first name
    @Column(length = 30, nullable = false)
    private String firstName;

    // middle name
    @Column(length = 30)
    private String middleName;

    // last name
    @Column(length = 30, nullable = false)
    private String lastName;

    // 납부자 신분증 유형
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private IdType idType;

    // 납부자 신분증 번호
    @Column(length = 250)
    @Convert(converter = CipherConverter.class)
    private String identifierNum;

    // 신분증상 이름(local language)
    @Column(length = 250, nullable = false)
    @Convert(converter = CipherConverter.class)
    private String nameInCountry;

    // 성별
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 생년월일
    @Column(nullable = false)
    private LocalDate birthDate;

    // 관계
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private RelationshipType relationshipType;

    // 국적
    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;

    // 전화번호
    @Column(length = 250, nullable = false)
    @Convert(converter = CipherConverter.class)
    private String telephone;

    // 이메일
    @Column(length = 250, nullable = false)
    @Convert(converter = CipherConverter.class)
    private String email;

    // 환불은행 코드
    @Column(length = 10, nullable = true)
    private String refundBankCode;

    // 환불계좌 번호
    @Column(length = 30, nullable = true)
    private String refundAccountNum;

    // pg사에서 발급한 납부자 아이디
    @Column(length = 100)
    private String pgPayerId;

    // 납부자 부가 정보(pg사 마다 custom한 정보 입력)
    /*@Column(columnDefinition = "text")
    @ElementCollection
    @Convert(converter = PayerAdditionalInfoConverter.class)
    private Map<String, String> additionalInfo;*/
    private String additionalInfo;

    // 수정일
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modDate;

    // 등록일
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDate;

}
