package pro.arcodeh.msgoba_2002_server.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Component
public class StorageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final S3Client s3Client;

    private S3Presigner s3Presigner;

    public StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    private S3Presigner getS3Presigner() {
        if (this.s3Presigner == null) {
            this.s3Presigner = S3Presigner.builder()
                    .s3Client(this.s3Client)
                    .build();
        }
        return this.s3Presigner;
    }

    public URL getUploadUrl(String objectKey, String contentType, Long expInSecs) {
        Duration expiration = Duration.ofSeconds(expInSecs);

        S3Presigner s3Presigner = getS3Presigner();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(expiration)
                .putObjectRequest(req -> req
                        .bucket(this.bucketName)
                        .key(objectKey)
                        .contentType(contentType)
                        .build())
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return presignedRequest.url();
    }

    public URL getDownloadUrl(String objectKey, Long expInSecs) {
        Duration expiration = Duration.ofSeconds(expInSecs);

        S3Presigner s3Presigner = getS3Presigner();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(expiration)
                .getObjectRequest(req -> req
                        .bucket(this.bucketName)
                        .key(objectKey)
                        .build())
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);

        return presignedRequest.url();
    }

    public void deleteS3Object(String objectKey) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(this.bucketName)
                .key(objectKey)
                .build();

        this.s3Client.deleteObject(deleteObjectRequest);
    }
}
