
package UniversityManagement.course.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import UniversityManagement.course.entity.Course;
import UniversityManagement.course.repository.CourseRepository;
import UniversityManagement.exception.ResourceNotFoundException;
import UniversityManagement.exception.ConflictException;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(
        transactionManager = "courseTransactionManager",
        readOnly = true
    )
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(
        transactionManager = "courseTransactionManager",
        readOnly = true
    )
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Course not found with ID: " + id
                    )
                );
    }

    @Transactional(transactionManager = "courseTransactionManager")
    public Course createCourse(Course course) {

        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new ConflictException("Course code already exists");
        }

        course.setId(null);

        return courseRepository.save(course);
    }

    @Transactional(transactionManager = "courseTransactionManager")
    public Course updateCourse(Long id, Course updatedCourse) {

        Course existingCourse = getCourseById(id);

        boolean codeExists =
                courseRepository.existsByCourseCode(
                    updatedCourse.getCourseCode()
                );

        if (codeExists &&
                !existingCourse.getCourseCode()
                    .equals(updatedCourse.getCourseCode())) {
            throw new ConflictException("Course code already exists");
        }

        existingCourse.setCourseName(updatedCourse.getCourseName());
        existingCourse.setCourseCode(updatedCourse.getCourseCode());
        existingCourse.setCredits(updatedCourse.getCredits());
        existingCourse.setInstructor(updatedCourse.getInstructor());

        return courseRepository.save(existingCourse);
    }

    @Transactional(transactionManager = "courseTransactionManager")
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }
}
