package pro.arcodeh.msgoba_2002_server.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileWithName extends BaseEntity {

    private String fullName;

    private String userId;

    private String phoneNumber;

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
