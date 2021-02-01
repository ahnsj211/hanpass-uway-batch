package com.hanpass.batch.common.repository;



import com.hanpass.batch.common.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Package :: com.hanpass.uway.repository
 * Developer :: Ahn Seong-jin
 * Date :: 2020-06-04
 * Description ::
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * 학생정보 조회
     * @param schoolName
     * @param studentId
     * @return
     */
    Optional<Student> findBySchoolNameAndStudentId(String schoolName, String studentId);
}
