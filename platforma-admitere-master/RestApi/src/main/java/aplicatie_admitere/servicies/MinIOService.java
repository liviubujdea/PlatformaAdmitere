package aplicatie_admitere.servicies;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.stereotype.Service;

import io.minio.errors.MinioException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;
@Service
public class MinIOService {
    final static String endPoint = "play.min.io";
    final static String accessKey = "minioadmin";
    final static String secretKey = "minioadmin";
    String bucketName;


    public String WriteToMinIO(MultipartFile arhiva, String email)
            throws InvalidKeyException, IllegalArgumentException, NoSuchAlgorithmException, IOException {
        String presignedUrl = null;
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(endPoint)
                    .credentials(accessKey, secretKey).build();

           String[] parts = email.split("@");
           parts[0] = parts[0].replace(("\""),"");
           bucketName = parts[0];

            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            InputStream inputStream = arhiva.getInputStream();
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object("arhiva.zip").stream(inputStream, inputStream.available(), -1).build());

            presignedUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object("arhiva.zip")
                    .expiry(24 * 7 * 3600)
                    .build());

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
        return presignedUrl;
    }
}
