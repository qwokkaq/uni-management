
package UniversityManagement.dto;

import java.util.List;

import UniversityManagement.student.entity.Student;

public class TranscriptDTO {

    private Student student;
    private List<TranscriptItemDTO> grades;
    private Integer totalCredits;
    private Double averageScore;

    public TranscriptDTO() {
    }

    public TranscriptDTO(
            Student student,
            List<TranscriptItemDTO> grades,
            Integer totalCredits,
            Double averageScore) {

        this.student = student;
        this.grades = grades;
        this.totalCredits = totalCredits;
        this.averageScore = averageScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<TranscriptItemDTO> getGrades() {
        return grades;
    }

    public void setGrades(List<TranscriptItemDTO> grades) {
        this.grades = grades;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }
}
