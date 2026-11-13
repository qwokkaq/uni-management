
package UniversityManagement.dto;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class TranscriptController {

    private final TranscriptService transcriptService;

    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @GetMapping("/{studentId}/transcript")
    public TranscriptDTO getTranscript(
            @PathVariable Long studentId) {

        return transcriptService.getStudentTranscript(studentId);
    }
}
