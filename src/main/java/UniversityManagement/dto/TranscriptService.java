
package UniversityManagement.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import UniversityManagement.student.entity.Student;
import UniversityManagement.student.repository.StudentRepository;

import UniversityManagement.course.entity.Course;
import UniversityManagement.course.repository.CourseRepository;

import UniversityManagement.grade.entity.Grade;
import UniversityManagement.grade.repository.GradeRepository;

import UniversityManagement.exception.ResourceNotFoundException;

@Service
public class TranscriptService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;

    public TranscriptService(
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
    }

    @Transactional(
        transactionManager = "gradeTransactionManager",
        readOnly = true
    )
    public TranscriptDTO getStudentTranscript(Long studentId) {

        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Student not found with ID: " + studentId
            ));

        List<Grade> studentGrades =
            gradeRepository.findByStudentId(studentId);

        List<TranscriptItemDTO> transcriptItems = new ArrayList<>();

        int totalCredits = 0;
        int totalScore = 0;

        for (Grade grade : studentGrades) {

            Course course = courseRepository.findById(grade.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Course not found with ID: " + grade.getCourseId()
                ));

            TranscriptItemDTO item = new TranscriptItemDTO(
                course.getId(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCredits(),
                grade.getScore(),
                grade.getLetterGrade(),
                grade.getGradedAt()
            );

            transcriptItems.add(item);

            totalCredits += course.getCredits();
            totalScore += grade.getScore();
        }

        double averageScore = studentGrades.isEmpty()
            ? 0.0
            : (double) totalScore / studentGrades.size();

        return new TranscriptDTO(
            student,
            transcriptItems,
            totalCredits,
            Math.round(averageScore * 100.0) / 100.0
        );
    }
}
