package pro.arcodeh.msgoba_2002_server.question;

import org.springframework.stereotype.Component;
import pro.arcodeh.msgoba_2002_server.NotFoundException;
import pro.arcodeh.msgoba_2002_server.models.Question;
import pro.arcodeh.msgoba_2002_server.repositories.QuestionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    public Question createQuestion(CreateQuestionDto dto) {
        Question newQuestion = new Question();
        newQuestion.setQuestion(dto.question());
        newQuestion.setOptions(dto.options());
        newQuestion.setCorrectOption(dto.correctOption());

        return this.questionRepository.save(newQuestion);
    }

    public Optional<Question> getQuestion(UUID questionId) {
        return this.questionRepository.findById(questionId);
    }

    public List<Question> getQuestions() {
        return this.questionRepository.findAll();
    }

    public Question updateQuestion(UUID questionId, UpdateQuestionDto dto) {
        Question oldQuestion = this.questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id: " + questionId + " was not found. Update failed"));

        if(dto.question() != null) {
            oldQuestion.setQuestion(dto.question());
        }
        if(dto.options() != null) {
            oldQuestion.setOptions(dto.options());
        }
        if(dto.correctOption() != null) {
            oldQuestion.setCorrectOption(dto.correctOption());
        }

        return this.questionRepository.save(oldQuestion);
    }

    public void deleteQuestion(UUID questionId) {
        this.questionRepository.deleteById(questionId);
    }
}
