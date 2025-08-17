package pro.arcodeh.msgoba_2002_server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pro.arcodeh.msgoba_2002_server.seeder.ProtoProfileSeeder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(ProtoProfileSeeder protoProfileSeeder) {
        return args -> {
            if(protoProfileSeeder.shouldSeed()) {
                protoProfileSeeder.runSeeding();
            }
        };
    }
}
