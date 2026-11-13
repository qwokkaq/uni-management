
package UniversityManagement.grade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import UniversityManagement.grade.entity.Grade;
import UniversityManagement.grade.repository.GradeRepository;
import UniversityManagement.student.repository.StudentRepository;
import UniversityManagement.course.repository.CourseRepository;
import UniversityManagement.exception.ResourceNotFoundException;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public GradeService(
            GradeRepository gradeRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    private void validateReferences(Long studentId, Long courseId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException(
                "Student not found with ID: " + studentId
            );
        }

        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException(
                "Course not found with ID: " + courseId
            );
        }
    }

    @Transactional(transactionManager = "gradeTransactionManager", readOnly = true)
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    @Transactional(transactionManager = "gradeTransactionManager", readOnly = true)
    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Grade not found with ID: " + id
            ));
    }

    @Transactional(transactionManager = "gradeTransactionManager", readOnly = true)
    public List<Grade> getGradesByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException(
                "Student not found with ID: " + studentId
            );
        }

        return gradeRepository.findByStudentId(studentId);
    }

    @Transactional(transactionManager = "gradeTransactionManager", readOnly = true)
    public List<Grade> getGradesByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException(
                "Course not found with ID: " + courseId
            );
        }

        return gradeRepository.findByCourseId(courseId);
    }

    @Transactional(transactionManager = "gradeTransactionManager")
    public Grade createGrade(Grade grade) {
        validateReferences(grade.getStudentId(), grade.getCourseId());

        grade.setId(null);

        return gradeRepository.save(grade);
    }

    @Transactional(transactionManager = "gradeTransactionManager")
    public Grade updateGrade(Long id, Grade updatedGrade) {
        Grade existingGrade = getGradeById(id);

        validateReferences(
            updatedGrade.getStudentId(),
            updatedGrade.getCourseId()
        );

        existingGrade.setStudentId(updatedGrade.getStudentId());
        existingGrade.setCourseId(updatedGrade.getCourseId());
        existingGrade.setScore(updatedGrade.getScore());
        existingGrade.setLetterGrade(updatedGrade.getLetterGrade());
        existingGrade.setGradedAt(updatedGrade.getGradedAt());

        return gradeRepository.save(existingGrade);
    }

    @Transactional(transactionManager = "gradeTransactionManager")
    public void deleteGrade(Long id) {
        Grade grade = getGradeById(id);
        gradeRepository.delete(grade);
    }
}
