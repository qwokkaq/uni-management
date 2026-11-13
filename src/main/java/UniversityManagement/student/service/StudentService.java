
package UniversityManagement.student.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import UniversityManagement.student.entity.Student;
import UniversityManagement.student.repository.StudentRepository;
import UniversityManagement.exception.ResourceNotFoundException;
import UniversityManagement.exception.ConflictException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get all students
    @Transactional(
        transactionManager = "studentTransactionManager",
        readOnly = true
    )
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    @Transactional(
        transactionManager = "studentTransactionManager",
        readOnly = true
    )
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Student not found with ID: " + id
                    )
                );
    }

    // Create student
    @Transactional(transactionManager = "studentTransactionManager")
    public Student createStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        student.setId(null);

        return studentRepository.save(student);
    }

    // Update student
    @Transactional(transactionManager = "studentTransactionManager")
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existingStudent = getStudentById(id);

        boolean emailExists =
                studentRepository.existsByEmail(updatedStudent.getEmail());

        if (emailExists &&
                !existingStudent.getEmail().equals(updatedStudent.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setMajor(updatedStudent.getMajor());
        existingStudent.setEnrollmentYear(updatedStudent.getEnrollmentYear());

        return studentRepository.save(existingStudent);
    }

    // Delete student
    @Transactional(transactionManager = "studentTransactionManager")
    public void deleteStudent(Long id) {

        Student student = getStudentById(id);

        studentRepository.delete(student);
    }
}
