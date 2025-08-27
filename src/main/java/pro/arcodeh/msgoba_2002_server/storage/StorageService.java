package pro.arcodeh.msgoba_2002_server.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Slf4j
@Component
public class StorageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;

    private S3Presigner s3Presigner;

    public StorageService(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public URL getUploadUrl(String objectKey, String contentType, Long expInSecs) {
        Duration expiration = Duration.ofSeconds(expInSecs);

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(expiration)
            .putObjectRequest(req -> req
                    .bucket(this.bucketName)
                    .key(objectKey)
                    .contentType(contentType)
                    .build())
            .build();

        PresignedPutObjectRequest presignedRequest = this.s3Presigner.presignPutObject(presignRequest);

        return presignedRequest.url();
    }

    public URL getDownloadUrl(String objectKey, Long expInSecs) {
        Duration expiration = Duration.ofSeconds(expInSecs);

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(expiration)
                .getObjectRequest(req -> req
                        .bucket(this.bucketName)
                        .key(objectKey)
                        .build())
                .build();

        PresignedGetObjectRequest presignedRequest = this.s3Presigner.presignGetObject(presignRequest);

        return presignedRequest.url();
    }

    public void deleteS3Object(String objectKey) throws S3Exception {
        try {
            this.s3Client.headObject(request -> request
                    .bucket(this.bucketName)
                    .key(objectKey)
                    .build());

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(this.bucketName)
                    .key(objectKey)
                    .build();

            this.s3Client.deleteObject(deleteObjectRequest);
            log.info("Deleted object with key: {}", objectKey);
        } catch(S3Exception e) {
            if(e.statusCode() == 404) {
                log.warn("Object with key {} not found, nothing to delete", objectKey);
                return;
            }
            throw new RuntimeException(e.getMessage());
        }
    }
}
