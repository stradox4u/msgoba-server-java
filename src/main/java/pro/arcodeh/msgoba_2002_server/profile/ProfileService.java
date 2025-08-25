package pro.arcodeh.msgoba_2002_server.profile;

import org.springframework.stereotype.Component;
import pro.arcodeh.msgoba_2002_server.models.Profile;
import pro.arcodeh.msgoba_2002_server.repositories.ProfileRepository;

@Component
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile() {

    }
}
