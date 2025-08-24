package pro.arcodeh.msgoba_2002_server.question;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pro.arcodeh.msgoba_2002_server.models.Question;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/question")
@PreAuthorize("hasRole(T(pro.arcodeh.msgoba_2002_server.enums.UserRoles).ADMIN.name())")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public Question createQuestion(@Valid @RequestBody CreateQuestionDto dto) {
        return this.questionService.createQuestion(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<Question> getQuestions() {
        System.out.println("Fetching all questions");
        return this.questionService.getQuestions();
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public Question updateQuestion(@Valid @RequestBody UpdateQuestionDto dto, @PathVariable UUID id) {
        return this.questionService.updateQuestion(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable UUID id) {
        this.questionService.deleteQuestion(id);
    }
}
