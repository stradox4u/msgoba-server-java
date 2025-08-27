package pro.arcodeh.msgoba_2002_server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileLimitedDtoWithName {
    private UUID id;

    private String fullName;

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
