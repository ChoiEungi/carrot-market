package numble.carrotmarket.s3api;

import com.amazonaws.services.s3.model.S3Object;
import numble.carrotmarket.domain.s3api.S3ApiProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class S3ApiProviderTest {

    private static final String filename = "carrot-market-erd";
    private static final String contentType = "png";
    private static final String filePath = "/test-img/carrot-market-erd.png";

    @Autowired
    private S3ApiProvider s3ApiProvider;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Test
    void uploadFile() throws IOException{
        MultipartFile multipartFile = getMockMultipartFile();
        s3ApiProvider.uploadFile(multipartFile, multipartFile.getOriginalFilename());

        S3Object s3Object = s3ApiProvider.getS3Object(multipartFile.getOriginalFilename());
        assertThat(s3Object.getBucketName()).isEqualTo(bucket);
        assertThat(s3Object.getKey()).isEqualTo(multipartFile.getOriginalFilename());
    }

    private MockMultipartFile getMockMultipartFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        FileInputStream fileInputStream = new FileInputStream(classPathResource.getFile());
        return new MockMultipartFile(filename, filename + "." + contentType, contentType, fileInputStream);
    }



}