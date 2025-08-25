package pro.arcodeh.msgoba_2002_server.question;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CheckAnswersDto(
        @NotEmpty.List(value = @NotEmpty)
        @Size(min = 3, message = "There must be at least 3 questions answered")
        List<@Valid AnswerDto> answers
) {
}
