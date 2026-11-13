
package UniversityManagement.grade.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import UniversityManagement.grade.entity.Grade;
import UniversityManagement.grade.service.GradeService;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @GetMapping("/{id}")
    public Grade getGradeById(@PathVariable Long id) {
        return gradeService.getGradeById(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Grade> getGradesByStudent(
            @PathVariable Long studentId) {

        return gradeService.getGradesByStudent(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Grade> getGradesByCourse(
            @PathVariable Long courseId) {

        return gradeService.getGradesByCourse(courseId);
    }

    @PostMapping
    public ResponseEntity<Grade> createGrade(
            @Valid @RequestBody Grade grade) {

        Grade created = gradeService.createGrade(grade);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public Grade updateGrade(
            @PathVariable Long id,
            @Valid @RequestBody Grade grade) {

        return gradeService.updateGrade(id, grade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(
            @PathVariable Long id) {

        gradeService.deleteGrade(id);

        return ResponseEntity.noContent().build();
    }
}
