package pro.arcodeh.msgoba_2002_server.profile;

import org.springframework.stereotype.Component;
import pro.arcodeh.msgoba_2002_server.models.BasicResponse;
import pro.arcodeh.msgoba_2002_server.models.Profile;
import pro.arcodeh.msgoba_2002_server.models.ProfileLimitedDto;
import pro.arcodeh.msgoba_2002_server.repositories.ProfileRepository;
import pro.arcodeh.msgoba_2002_server.storage.StorageService;

import java.net.URL;
import java.util.List;

@Component
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final StorageService storageService;

    public ProfileService(ProfileRepository profileRepository, StorageService storageService) {
        this.profileRepository = profileRepository;
        this.storageService = storageService;
    }

    public Profile createProfile(CreateProfileDto dto) {
        Profile profile = Profile.builder()
                .userId(dto.userId())
                .phoneNumber(dto.phoneNumber())
                .occupationStatus(dto.occupationStatus())
                .occupation(dto.occupation())
                .placeOfWork(dto.placeOfWork())
                .placeOfResidence(dto.placeOfResidence())
                .hobbies(dto.hobbies())
                .birthday(dto.birthday())
                .maritalStatus(dto.maritalStatus())
                .nickname(dto.nickname())
                .finalClass(dto.finalClass())
                .excoPosition(dto.excoPosition())
                .bio(dto.bio())
                .profilePictureUrl(dto.profilePictureUrl())
                .build();

        return this.profileRepository.save(profile);
    }

    public Profile getProfileByUserId(String userId) {
        Profile userProfile = this.profileRepository.findByUserId(userId);
        URL imgUrl = this.storageService.getDownloadUrl(userProfile.getProfilePictureUrl(), 3600L);
        userProfile.setProfilePictureUrl(String.valueOf(imgUrl));

        return userProfile;
    }

    public List<ProfileLimitedDto> getRandomProfiles(Integer count, String excludeUserId) {
        List<ProfileLimitedDto> randomProfiles = this.profileRepository.getRandomProfiles(count, excludeUserId);

        return randomProfiles.stream()
                .map(profile -> {
                    URL imgUrl = this.storageService.getDownloadUrl(profile.getProfilePictureUrl(), 3600L);
                    profile.setProfilePictureUrl(String.valueOf(imgUrl));

                    return profile;
                }).toList();
    }

    public Profile updateProfile(UpdateProfileDto dto, String userId) {
//        Only set fields that are supplied in UpdateProfileDto
        Profile oldProfile = this.profileRepository.findByUserId(userId);

        if(dto.phoneNumber() != null) {
            oldProfile.setPhoneNumber(dto.phoneNumber());
        }

        if(dto.occupationStatus() != null) {
            oldProfile.setOccupationStatus(dto.occupationStatus());
        }

        if(dto.occupation() != null) {
            oldProfile.setOccupation(dto.occupation());
        }

        Profile newProfile = this.profileRepository.save(oldProfile);
        return newProfile;
    }

    public BasicResponse deleteProfile(String userId) {
        Boolean profileExists = this.profileRepository.existsByUserId(userId);

        if(!profileExists) {
            return new BasicResponse("Profile does not exist", false);
        }

        this.profileRepository.deleteByUserId(userId);
        return new BasicResponse("Successfully deleted profile", true);
    }
}
