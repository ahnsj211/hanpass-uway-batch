package com.hanpass.batch.common.entity;

import com.hanpass.batch.common.converter.CipherConverter;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Package :: com.hanpass.uway.common.model
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-04
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
@Table(name="student", indexes = {
        @Index(name = "student_idx1", columnList = "schoolName"),
        @Index(name = "student_idx2", columnList = "studentId"),
        @Index(name = "student_idx2", columnList = "name")
})
public class Student implements Serializable {

    // student pk
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long studentSeq;

    // Student(1) -> VirtualAccount(1)
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="virtual_account_seq")
    private VirtualAccount virtualAccount;

    // 학교명
    @Column(length = 50, nullable = false)
    private String schoolName;

    // 학번
    // TODO : 학교id uway에서 제공 받아야 하며 학교id와 학생 id uk 설정 필요
    @Column(length = 250, nullable = false)
    @Convert(converter = CipherConverter.class)
    private String studentId;

    // 전공
    @Column(length = 50, nullable = false)
    private String major;

    // 학생 명
    @Column(length = 250, nullable = false)
    @Convert(converter = CipherConverter.class)
    private String name;

    // 학생 생년월일
    @Column(nullable = false)
    private LocalDate birthDate;

    // 학생 연락처
    @Column(length = 250, nullable = false)
    @Convert(converter = CipherConverter.class)
    private String contact;

}
