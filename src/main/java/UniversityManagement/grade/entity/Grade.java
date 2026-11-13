
package UniversityManagement.grade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "grades",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"student_id", "course_id", "graded_at"}
        )
    }
)
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotNull(message = "Course ID is required")
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score cannot be below 0")
    @Max(value = 100, message = "Score cannot exceed 100")
    @Column(nullable = false)
    private Integer score;

    @NotBlank(message = "Letter grade is required")
    @Column(name = "letter_grade", nullable = false)
    private String letterGrade;

    @NotNull(message = "Graded date is required")
    @Column(name = "graded_at", nullable = false)
    private LocalDateTime gradedAt;

    public Grade() {
    }

    public Grade(Long studentId, Long courseId,
                 Integer score, String letterGrade,
                 LocalDateTime gradedAt) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
        this.letterGrade = letterGrade;
        this.gradedAt = gradedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (gradedAt == null) {
            gradedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public LocalDateTime getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(LocalDateTime gradedAt) {
        this.gradedAt = gradedAt;
    }
}
