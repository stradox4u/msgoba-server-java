package pro.arcodeh.msgoba_2002_server.question;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateQuestionDto(
        @Size(min = 5, max = 255, message = "Question must be between 5 and 255 characters")
        String question,

        @NotEmpty.List(value = @NotEmpty)
        @Size(min = 2, max = 3, message = "There must be between 2 and 3 options")
        List<@NotEmpty String> options,

        @Min(value = 0, message = "Correct option must be a positive index")
        Integer correctOption
) {
}
