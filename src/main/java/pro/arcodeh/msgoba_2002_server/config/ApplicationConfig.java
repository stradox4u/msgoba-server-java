package pro.arcodeh.msgoba_2002_server.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public CsvMapper csvMapper() {
        return new CsvMapper();
    }
}
