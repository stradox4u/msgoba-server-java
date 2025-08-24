package pro.arcodeh.msgoba_2002_server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Profile extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String occupationStatus;

    @Column(nullable = false)
    private String occupation;

    private String placeOfWork;

    @Column(nullable = false)
    private String placeOfResidence;

    @Column(nullable = false)
    private List<String> hobbies;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String maritalStatus;

    private String nickname;

    @Column(nullable = false)
    private String finalClass;

    private String excoPosition;

    private String bio;

    private String profilePictureUrl;
}
