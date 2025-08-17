package pro.arcodeh.msgoba_2002_server.seeder;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pro.arcodeh.msgoba_2002_server.models.ProtoProfile;
import pro.arcodeh.msgoba_2002_server.models.ProtoProfileDao;
import pro.arcodeh.msgoba_2002_server.repositories.ProtoProfileRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class ProtoProfileSeeder implements SeederInterface{
    private final CsvMapper csvMapper;
    private final ProtoProfileRepository protoProfileRepository;

    public ProtoProfileSeeder(ProtoProfileRepository protoProfileRepository, CsvMapper csvMapper) {
        this.csvMapper = csvMapper;
        this.protoProfileRepository = protoProfileRepository;
    }

    @Override
    public boolean shouldSeed() {
        return this.protoProfileRepository.count() != 53;
    }

    @Override
    public void runSeeding() {
        File profilesFile = new File(Objects.requireNonNull(this.getClass().getResource("/data/album.csv")).getFile());
        List<ProtoProfileDao> protoProfileDaos = this.readCsv(profilesFile);
        List<ProtoProfile> protoProfiles = new ArrayList<>();
        protoProfileDaos.forEach(dao -> {
            String dateOfBirthString = dao.getDateOfBirth();
            if(dateOfBirthString.isEmpty()) {
                dateOfBirthString = "1970-01-01";
            }
            if(dateOfBirthString.startsWith("00")) {
                dateOfBirthString = "19" + dateOfBirthString.substring(2);
            }
            LocalDate localDate = LocalDate.parse(dateOfBirthString);
            LocalDateTime dOfB = localDate.atTime(12, 0);
            var protoProfile = ProtoProfile.builder()
                    .email(dao.getEmail())
                    .dateOfBirth(dOfB)
                    .phoneNumber(dao.getPhoneNumber())
                    .nickname(dao.getNickname().isEmpty() ? null : dao.getNickname())
                    .createdAt(LocalDateTime.now())
                    .build();
            protoProfiles.add(protoProfile);
        });

        this.protoProfileRepository.saveAll(protoProfiles);
        log.info("Successfully seeded proto-profiles");
    }

    private List<ProtoProfileDao> readCsv(File profilesFile) {
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        try (MappingIterator<ProtoProfileDao> protoProfileMappingIterator = this.csvMapper
                .readerFor(ProtoProfileDao.class)
                .with(schema)
                .readValues(profilesFile)) {

            return protoProfileMappingIterator.readAll();
        } catch(IOException ex) {
            throw new RuntimeException("Failed to read CSV data", ex);
        }
    }
}
