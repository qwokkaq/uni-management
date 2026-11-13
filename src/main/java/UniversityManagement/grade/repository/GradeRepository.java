
package UniversityManagement.grade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import UniversityManagement.grade.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    List<Grade> findByStudentId(Long studentId);

    List<Grade> findByCourseId(Long courseId);
}
