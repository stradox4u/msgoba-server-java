package pro.arcodeh.msgoba_2002_server.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Component;
import pro.arcodeh.msgoba_2002_server.NotFoundException;
import pro.arcodeh.msgoba_2002_server.models.*;
import pro.arcodeh.msgoba_2002_server.repositories.ProfileRepository;
import pro.arcodeh.msgoba_2002_server.storage.StorageService;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Component
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final StorageService storageService;

    private final FirebaseAuth firebaseAuth;

    public ProfileService(
            ProfileRepository profileRepository,
            StorageService storageService,
            FirebaseAuth firebaseAuth
            ) {
        this.profileRepository = profileRepository;
        this.storageService = storageService;
        this.firebaseAuth = firebaseAuth;
    }

    public Profile createProfile(CreateProfileDto dto, String userId) {
        Profile profile = Profile.builder()
                .userId(userId)
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

    public ProfileWithName getProfileByUserId(String userId) throws NotFoundException, FirebaseAuthException {
        Profile userProfile = this.profileRepository.findByUserId(userId);
        if(userProfile == null) {
            throw new NotFoundException("User profile not found");
        }
        URL imgUrl = this.storageService.getDownloadUrl(userProfile.getProfilePictureUrl(), 3600L);
        userProfile.setProfilePictureUrl(String.valueOf(imgUrl));

        UserRecord userRecord = this.firebaseAuth.getUser(userProfile.getUserId());
        String fullName = userRecord.getDisplayName();

        return ProfileWithName.builder()
                .id(userProfile.getId())
                .fullName(fullName)
                .userId(userProfile.getUserId())
                .phoneNumber(userProfile.getPhoneNumber())
                .occupationStatus(userProfile.getOccupationStatus())
                .occupation(userProfile.getOccupation())
                .placeOfWork(userProfile.getPlaceOfWork())
                .placeOfResidence(userProfile.getPlaceOfResidence())
                .hobbies(userProfile.getHobbies())
                .birthday(userProfile.getBirthday())
                .maritalStatus(userProfile.getMaritalStatus())
                .nickname(userProfile.getNickname())
                .finalClass(userProfile.getFinalClass())
                .excoPosition(userProfile.getExcoPosition())
                .bio(userProfile.getBio())
                .profilePictureUrl(userProfile.getProfilePictureUrl())
                .build();
    }

    public ProfileLimitedDtoWithName getProfileById(UUID id) throws FirebaseAuthException {
        Profile profile = this.profileRepository.findById(id)
                .orElseThrow();

        UserRecord userRecord = this.firebaseAuth.getUser(profile.getUserId());
        String fullName = userRecord.getDisplayName();
        URL imgUrl = this.storageService.getDownloadUrl(profile.getProfilePictureUrl(), 3600L);
        profile.setProfilePictureUrl(String.valueOf(imgUrl));

        return ProfileLimitedDtoWithName.builder()
                .id(profile.getId())
                .fullName(fullName)
                .userId(profile.getUserId())
                .occupationStatus(profile.getOccupationStatus())
                .occupation(profile.getOccupation())
                .placeOfWork(profile.getPlaceOfWork())
                .placeOfResidence(profile.getPlaceOfResidence())
                .hobbies(profile.getHobbies())
                .birthday(profile.getBirthday())
                .maritalStatus(profile.getMaritalStatus())
                .nickname(profile.getNickname())
                .finalClass(profile.getFinalClass())
                .excoPosition(profile.getExcoPosition())
                .bio(profile.getBio())
                .profilePictureUrl(profile.getProfilePictureUrl())
                .build();
    }

    public List<ProfileLimitedDtoWithName> getRandomProfiles(Integer count, String excludeUserId) throws FirebaseAuthException {
        List<ProfileLimitedDto> randomProfiles = this.profileRepository.getRandomProfiles(count, excludeUserId);

        return randomProfiles.stream()
                .map(profile -> {
                    ProfileLimitedDtoWithName limitedProfileWithName = null;
                    URL imgUrl = this.storageService.getDownloadUrl(profile.getProfilePictureUrl(), 3600L);
                    profile.setProfilePictureUrl(String.valueOf(imgUrl));

                    try {
                        UserRecord userRecord = this.firebaseAuth.getUser(profile.getUserId());
                        String fullName = userRecord.getDisplayName();
                        limitedProfileWithName = ProfileLimitedDtoWithName.builder()
                                .id(profile.getId())
                                .fullName(fullName)
                                .userId(profile.getUserId())
                                .occupationStatus(profile.getOccupationStatus())
                                .occupation(profile.getOccupation())
                                .placeOfWork(profile.getPlaceOfWork())
                                .placeOfResidence(profile.getPlaceOfResidence())
                                .hobbies(profile.getHobbies())
                                .birthday(profile.getBirthday())
                                .maritalStatus(profile.getMaritalStatus())
                                .nickname(profile.getNickname())
                                .finalClass(profile.getFinalClass())
                                .excoPosition(profile.getExcoPosition())
                                .bio(profile.getBio())
                                .profilePictureUrl(profile.getProfilePictureUrl())
                                .build();
                    } catch (FirebaseAuthException e) {
                        throw new RuntimeException(e);
                    }

                    return limitedProfileWithName;
                }).toList();
    }

    public Profile updateProfile(UpdateProfileDto dto, String userId) {
        Profile oldProfile = this.profileRepository.findByUserId(userId);
        System.out.println("Old profile before update: " + oldProfile.toString());
        System.out.println("Update DTO: " + dto.toString());

        if(dto.phoneNumber() != null) {
            oldProfile.setPhoneNumber(dto.phoneNumber());
        }

        if(dto.occupationStatus() != null) {
            oldProfile.setOccupationStatus(dto.occupationStatus());
        }

        if(dto.occupation() != null) {
            oldProfile.setOccupation(dto.occupation());
        }

        if(dto.placeOfWork() != null) {
            oldProfile.setPlaceOfWork(dto.placeOfWork());
        }

        if(dto.placeOfResidence() != null) {
            oldProfile.setPlaceOfResidence(dto.placeOfResidence());
        }

        if(dto.hobbies() != null) {
            oldProfile.setHobbies(dto.hobbies());
        }

        if(dto.birthday() != null) {
            oldProfile.setBirthday(dto.birthday());
        }

        if(dto.maritalStatus() != null) {
            oldProfile.setMaritalStatus(dto.maritalStatus());
        }

        if(dto.nickname() != null) {
            oldProfile.setNickname(dto.nickname());
        }

        if(dto.finalClass() != null) {
            oldProfile.setFinalClass(dto.finalClass());
        }

        if(dto.excoPosition() != null) {
            oldProfile.setExcoPosition(dto.excoPosition());
        }

        if(dto.bio() != null) {
            oldProfile.setBio(dto.bio());
        }

        if(dto.profilePictureUrl() != null) {
            System.out.println("Updating profile picture URL to: " + dto.profilePictureUrl());
            oldProfile.setProfilePictureUrl(dto.profilePictureUrl());
        }

        Profile savedProfile = this.profileRepository.save(oldProfile);
        URL profilePicUrl = this.storageService.getDownloadUrl(savedProfile.getProfilePictureUrl(), 3600L);
        savedProfile.setProfilePictureUrl(String.valueOf(profilePicUrl));
        return savedProfile;
    }

    public BasicResponse deleteProfile(String userId) {
        Boolean profileExists = this.profileRepository.existsByUserId(userId);

        if(!profileExists) {
            return new BasicResponse("Profile does not exist", false);
        }

        Profile existingProfile = this.profileRepository.findByUserId(userId);
        String profilePictureUrl = existingProfile.getProfilePictureUrl();

        this.storageService.deleteS3Object(profilePictureUrl);

        this.profileRepository.deleteByUserId(userId);
        return new BasicResponse("Successfully deleted profile", true);
    }
}
