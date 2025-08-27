package pro.arcodeh.msgoba_2002_server.storage;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.arcodeh.msgoba_2002_server.models.BasicResponse;

import java.net.URL;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upload-url")
    public URL getSignedUploadUrl(@Valid @RequestBody GetUploadUrlDto dto) {
        return this.storageService.getUploadUrl(dto.key(), dto.contentType(), 600L);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
    public BasicResponse deleteS3Object(@RequestParam String path) {
        this.storageService.deleteS3Object(path);

        return new BasicResponse("Object successfully deleted", true);
    }
}
