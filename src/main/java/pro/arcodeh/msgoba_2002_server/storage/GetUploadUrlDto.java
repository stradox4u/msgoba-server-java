package pro.arcodeh.msgoba_2002_server.storage;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GetUploadUrlDto(
        @NotEmpty
        @NotNull
        String key,

        @NotEmpty
        @NotNull
        String contentType
) {
}
