package pro.arcodeh.msgoba_2002_server.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProtoProfileDao {
    @JsonProperty("Date of Birth")
    private String dateOfBirth;

    @JsonProperty("Telephone")
    @Column(nullable = false)
    private String phoneNumber;

    @JsonProperty("Email")
    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty("Nickname")
    private String nickname;
}
