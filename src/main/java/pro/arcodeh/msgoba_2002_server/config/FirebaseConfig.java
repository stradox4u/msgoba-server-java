package pro.arcodeh.msgoba_2002_server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    private final String googleAcctKey;

    public FirebaseConfig(@Value("${spring.GOOGLE_ACCOUNT_KEY}") String googleAcctKey) {
        this.googleAcctKey = googleAcctKey;
    }

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        ClassPathResource resource = new ClassPathResource(googleAcctKey);
        InputStream serviceAccount = resource.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if(FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }
}
