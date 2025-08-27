package pro.arcodeh.msgoba_2002_server.profile;

import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pro.arcodeh.msgoba_2002_server.models.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public Profile createProfile(@Valid @RequestBody CreateProfileDto dto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return this.profileService.createProfile(dto, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ResponseEntity<?> getMyProfile() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            ProfileWithName profile = this.profileService.getProfileByUserId(userId);
            return ResponseEntity.ok(profile);
        } catch(Exception ex) {
            return ResponseEntity.notFound()
                    .build();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable UUID id) {
        try {
            ProfileLimitedDtoWithName profile = this.profileService.getProfileById(id);
            if(profile == null) {
                return ResponseEntity.notFound()
                        .build();
            }
            return ResponseEntity.ok(profile);
        } catch (FirebaseAuthException e) {
            log.error("FirebaseAuthException from getProfileById: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .build();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/random")
    public ResponseEntity<?> getRandomProfiles(@RequestParam Integer count) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            List<ProfileLimitedDtoWithName> limitedProfiles = this.profileService.getRandomProfiles(count, userId);
            return ResponseEntity.ok(limitedProfiles);
        } catch (FirebaseAuthException e) {
            log.error("FirebaseAuthException from getRandomProfiles: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/")
    public Profile updateMyProfile(@Valid @RequestBody UpdateProfileDto dto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return this.profileService.updateProfile(dto, userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/")
    public BasicResponse deleteProfile() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return this.profileService.deleteProfile(userId);
    }
}
