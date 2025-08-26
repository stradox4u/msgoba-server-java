package pro.arcodeh.msgoba_2002_server.profile;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pro.arcodeh.msgoba_2002_server.models.BasicResponse;
import pro.arcodeh.msgoba_2002_server.models.Profile;
import pro.arcodeh.msgoba_2002_server.models.ProfileLimitedDto;

import java.util.List;

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
    public Profile getMyProfile() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return this.profileService.getProfileByUserId(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/random")
    public List<ProfileLimitedDto> getRandomProfiles() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return this.profileService.getRandomProfiles(5, userId);
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
