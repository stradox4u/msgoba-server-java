package pro.arcodeh.msgoba_2002_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pro.arcodeh.msgoba_2002_server.models.Profile;
import pro.arcodeh.msgoba_2002_server.models.ProfileLimitedDto;

import java.util.List;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Profile findByUserId(String userId);

    Boolean existsByUserId(String userId);

    @Modifying
    @Query("DELETE FROM Profile p WHERE p.userId = :userId")
    void deleteByUserId(String userId);

    @Query("SELECT new pro.arcodeh.msgoba_2002_server.models.ProfileLimitedDto(p.id, p.userId, p.occupationStatus, p.occupation, p.placeOfWork, p.placeOfResidence, p.hobbies, p.birthday, p.maritalStatus, p.nickname, p.finalClass, p.excoPosition, p.bio, p.profilePictureUrl) FROM Profile p WHERE p.userId <> :excludeUserId ORDER BY function('RANDOM') LIMIT :count")
    List<ProfileLimitedDto> getRandomProfiles(Integer count, String excludeUserId);
}
