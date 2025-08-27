package pro.arcodeh.msgoba_2002_server.profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record UpdateProfileDto(

       @Size(min = 11, message = "Phone number must be at least 11 digits")
       String phoneNumber,

       String occupationStatus,

       String occupation,

       String placeOfWork,

        String placeOfResidence,

        List<@NotEmpty(message = "Please enter non-empty hobbies") String> hobbies,

        @Past
        LocalDate birthday,

        String maritalStatus,

        String nickname,

        @Size(min = 8, max = 8, message = "Final class must be 8 characters")
        String finalClass,

        String excoPosition,

        String bio,

        String profilePictureUrl
) {
}
