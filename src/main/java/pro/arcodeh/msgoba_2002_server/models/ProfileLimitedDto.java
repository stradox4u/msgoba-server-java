package pro.arcodeh.msgoba_2002_server.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileLimitedDto{

    private UUID id;

    @Column(nullable = false, unique = true)
    private String userId;

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
