package pro.arcodeh.msgoba_2002_server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity {

    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonProperty("createdAt")
    @Column(updatable = false, nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}
