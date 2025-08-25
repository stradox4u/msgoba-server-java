package pro.arcodeh.msgoba_2002_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.arcodeh.msgoba_2002_server.models.CheckAnswerDao;
import pro.arcodeh.msgoba_2002_server.models.Question;
import pro.arcodeh.msgoba_2002_server.models.QuestionLimitedDto;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query("SELECT new pro.arcodeh.msgoba_2002_server.models.CheckAnswerDao(q.id, q.correctOption) FROM Question q WHERE q.id IN :ids")
    List<CheckAnswerDao> findAllByIdAndSelectCorrectOption(List<UUID> ids);

    @Query("SELECT new pro.arcodeh.msgoba_2002_server.models.QuestionLimitedDto(q.id, q.question, q.options) FROM Question q ORDER BY function('RANDOM') LIMIT :count")
    List<QuestionLimitedDto> findRandomQuestions(Integer count);
}
