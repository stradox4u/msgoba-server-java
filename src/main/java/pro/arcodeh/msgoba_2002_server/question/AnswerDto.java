package pro.arcodeh.msgoba_2002_server.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record AnswerDto(
        UUID questionId,

        @NotNull
        @PositiveOrZero
        Integer providedAnswer
) {
}
