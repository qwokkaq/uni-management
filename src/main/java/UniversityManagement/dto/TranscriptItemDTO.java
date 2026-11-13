
package UniversityManagement.dto;

import java.time.LocalDateTime;

public class TranscriptItemDTO {

    private Long courseId;
    private String courseCode;
    private String courseName;
    private Integer credits;
    private Integer score;
    private String letterGrade;
    private LocalDateTime gradedAt;

    public TranscriptItemDTO(
            Long courseId,
            String courseCode,
            String courseName,
            Integer credits,
            Integer score,
            String letterGrade,
            LocalDateTime gradedAt) {

        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.score = score;
        this.letterGrade = letterGrade;
        this.gradedAt = gradedAt;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getCredits() {
        return credits;
    }

    public Integer getScore() {
        return score;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public LocalDateTime getGradedAt() {
        return gradedAt;
    }
}
