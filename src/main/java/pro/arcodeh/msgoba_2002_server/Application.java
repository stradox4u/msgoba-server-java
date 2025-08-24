package pro.arcodeh.msgoba_2002_server;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.arcodeh.msgoba_2002_server.seeder.ProtoProfileSeeder;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(ProtoProfileSeeder protoProfileSeeder, FirebaseApp firebaseApp) {
        return args -> {
            if(protoProfileSeeder.shouldSeed()) {
                protoProfileSeeder.runSeeding();
            }
        };
    }
}
