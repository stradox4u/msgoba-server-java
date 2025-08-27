package pro.arcodeh.msgoba_2002_server.question;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pro.arcodeh.msgoba_2002_server.models.BasicResponse;
import pro.arcodeh.msgoba_2002_server.models.Question;
import pro.arcodeh.msgoba_2002_server.models.QuestionLimitedDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PreAuthorize("hasRole(T(pro.arcodeh.msgoba_2002_server.enums.UserRoles).ADMIN.name())")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public Question createQuestion(@Valid @RequestBody CreateQuestionDto dto) {
        return this.questionService.createQuestion(dto);
    }

    @PreAuthorize("hasRole(T(pro.arcodeh.msgoba_2002_server.enums.UserRoles).ADMIN.name())")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin")
    public ResponseEntity<?> getQuestionsAdmin(@RequestParam(required = false) UUID id) {
        if(id != null) {
            Question question = this.questionService.getQuestionById(id);
            if(question != null) {
                return ResponseEntity.ok(question);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BasicResponse("Question not found", false));
            }
        } else {
            List<Question> questions = this.questionService.getQuestions();
            return ResponseEntity.ok(questions);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<QuestionLimitedDto> getRandomQuestions() {
        return this.questionService.getRandomQuestions(3);
    }

    @PreAuthorize("hasRole(T(pro.arcodeh.msgoba_2002_server.enums.UserRoles).ADMIN.name())")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public Question updateQuestion(@Valid @RequestBody UpdateQuestionDto dto, @PathVariable UUID id) {
        return this.questionService.updateQuestion(id, dto);
    }

    @PreAuthorize("hasRole(T(pro.arcodeh.msgoba_2002_server.enums.UserRoles).ADMIN.name())")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable UUID id) {
        this.questionService.deleteQuestion(id);
    }

    @PostMapping("/answers")
    public ResponseEntity<BasicResponse> checkAnswers(@Valid @RequestBody CheckAnswersDto dto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        BasicResponse result = this.questionService.checkAnswers(dto, userId);

        if(result.success()) {
            return ResponseEntity.ok(result);
        } else {
           return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/verified")
    public BasicResponse checkQuestionsVerified() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.questionService.checkQuestionsVerified(userId);
    }
}
