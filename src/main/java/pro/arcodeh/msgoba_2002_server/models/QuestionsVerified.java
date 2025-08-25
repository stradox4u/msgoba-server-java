package pro.arcodeh.msgoba_2002_server.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuestionsVerified {

    public QuestionsVerified() {
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @SequenceGenerator(name = "questions_verified_seq", sequenceName = "questions_verified_seq", allocationSize =  1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questions_verified_seq")
    private Integer id;

    @JsonProperty("userId")
    @Column(nullable = false, name = "user_id")
    private String userId;

    @JsonProperty("createdAt")
    @Column(updatable = false, nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}

