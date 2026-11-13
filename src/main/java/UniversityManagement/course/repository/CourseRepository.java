
package UniversityManagement.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import UniversityManagement.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCourseCode(String courseCode);

    Optional<Course> findByCourseCode(String courseCode);
}
