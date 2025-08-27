package pro.arcodeh.msgoba_2002_server.profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record CreateProfileDto(

        @NotEmpty
        @NotNull
        @Size(min = 11, message = "Phone number must be at least 11 digits")
        String phoneNumber,

        @NotEmpty
        @NotNull
        String occupationStatus,

        @NotEmpty
        @NotNull
        String occupation,

        String placeOfWork,

        @NotEmpty
        @NotNull
        String placeOfResidence,

        @NotEmpty.List(value = @NotEmpty)
        @Size(min = 1, message = "You must provide at least one hobby")
        List<@NotEmpty String> hobbies,

        @NotNull
        @Past
        LocalDate birthday,

        @NotEmpty
        @NotNull
        String maritalStatus,

        String nickname,

        @NotEmpty
        @NotNull
        @Size(min = 8, max = 8, message = "Final class must be 8 characters")
        String finalClass,

        String excoPosition,

        String bio,

        String profilePictureUrl
) {
}
