package pro.arcodeh.msgoba_2002_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.arcodeh.msgoba_2002_server.models.QuestionsVerified;

public interface QuestionsVerifiedRepository extends JpaRepository<QuestionsVerified, Integer> {
    boolean existsByUserId(String userId);
}
