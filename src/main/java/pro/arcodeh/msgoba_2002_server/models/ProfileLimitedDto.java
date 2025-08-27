package pro.arcodeh.msgoba_2002_server.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileLimitedDto{

    private UUID id;

    private String userId;

    private String occupationStatus;

    private String occupation;

    private String placeOfWork;

    private String placeOfResidence;

    private List<String> hobbies;

    private LocalDate birthday;

    private String maritalStatus;

    private String nickname;

    private String finalClass;

    private String excoPosition;

    private String bio;

    private String profilePictureUrl;
}
