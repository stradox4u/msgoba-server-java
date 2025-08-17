package pro.arcodeh.msgoba_2002_server.question;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateQuestionDto(
        @Size(min = 5, max = 255, message = "Question must be between 5 and 255 characters")
        String question,

        List<@NotEmpty(message = "Empty options are not allowed") String> options,

        @Min(value = 0, message = "Correct option must be a positive index")
        Integer correctOption) {
}
