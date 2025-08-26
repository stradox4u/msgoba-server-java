package pro.arcodeh.msgoba_2002_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

    @Value("${cloud.aws.s3.credentials.access_key}")
    private String accessKeyId;

    @Value("${cloud.aws.s3.credentials.secret_key}")
    private String secretAccessKey;


    @Bean
    public S3Client s3Client() {
        Region region = Region.AF_SOUTH_1;
        AwsCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
