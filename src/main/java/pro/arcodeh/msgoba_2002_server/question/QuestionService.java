package pro.arcodeh.msgoba_2002_server.question;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.arcodeh.msgoba_2002_server.NotFoundException;
import pro.arcodeh.msgoba_2002_server.models.BasicResponse;
import pro.arcodeh.msgoba_2002_server.models.CheckAnswerDao;
import pro.arcodeh.msgoba_2002_server.models.Question;
import pro.arcodeh.msgoba_2002_server.models.QuestionsVerified;
import pro.arcodeh.msgoba_2002_server.repositories.QuestionRepository;
import pro.arcodeh.msgoba_2002_server.repositories.QuestionsVerifiedRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionsVerifiedRepository questionsVerifiedRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            QuestionsVerifiedRepository questionsVerifiedRepository
    ) {
        this.questionRepository = questionRepository;
        this.questionsVerifiedRepository = questionsVerifiedRepository;
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
        List<Question> allQuestions = this.questionRepository.findAll();
        log.info("Retrieved {} questions from the database", allQuestions.size());
        return allQuestions;
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
            List<String> options = oldQuestion.getOptions();
            int correctOption = dto.correctOption();
            if(options.isEmpty() || correctOption < 0 || correctOption >= options.size()) {
                throw new IllegalArgumentException("correctOption index is out of bounds of the options array");
            }
            oldQuestion.setCorrectOption(dto.correctOption());
        }

        return this.questionRepository.save(oldQuestion);
    }

    public void deleteQuestion(UUID questionId) {
        boolean questionExists = this.questionRepository.existsById(questionId);

        if(!questionExists) {
            throw new NotFoundException("Question not found. Deletion failed.");
        }

        this.questionRepository.deleteById(questionId);
    }

    public BasicResponse checkAnswers(CheckAnswersDto dto, String userId) {
        List<UUID> questionIds = dto.answers().stream()
                .map(AnswerDto::questionId)
                .toList();
        List<CheckAnswerDao> questions = this.questionRepository.findAllByIdAndSelectCorrectOption(questionIds);

        int score = 0;
        for(AnswerDto answer : dto.answers()) {
            var question = questions.stream()
                    .filter(q -> q.getId().equals(answer.questionId()))
                    .findFirst();
            if(question.isPresent() && question.get().getCorrectOption() == answer.providedAnswer()) {
                score++;
            }
        }

        if(score == dto.answers().size()) {
            QuestionsVerified verification = new QuestionsVerified();
            verification.setUserId(userId);

            this.questionsVerifiedRepository.save(verification);

            return new BasicResponse("Congratulations! You have passed the test.", true);
        }
        return new BasicResponse("Sorry! You have failed the test.", false);
    }

    public BasicResponse checkQuestionsVerified(String userId) {
        boolean isVerified = this.questionsVerifiedRepository.existsByUserId(userId);
        if(isVerified) {
            return new BasicResponse("User has verified questions.", true);
        } else {
            return new BasicResponse("User has not verified questions.", false);
        }
    }
}
