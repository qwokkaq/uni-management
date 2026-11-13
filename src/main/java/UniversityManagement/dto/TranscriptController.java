
package UniversityManagement.dto;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class TranscriptController {

    private final TranscriptService transcriptService;

    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    // Get transcript for one student
    @GetMapping("/{studentId}/transcript")
    public TranscriptDTO getTranscript(
            @PathVariable Long studentId) {

        return transcriptService.getStudentTranscript(studentId);
    }

    // Get transcripts for all students
    @GetMapping("/transcripts")
    public List<TranscriptDTO> getAllTranscripts() {

        return transcriptService.getAllStudentTranscripts();
    }
}
