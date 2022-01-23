package numble.carrotmarket.domain.s3api;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import numble.carrotmarket.domain.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class S3ApiProvider {

    private static final String BASE_URL = "https://numble-carrot-market.s3.ap-northeast-2.amazonaws.com/";

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile multipartFile, String filename){
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, filename, multipartFile.getInputStream(), null));
        } catch (IOException e) {
            throw new CustomException("파일 업로드를 실패했습니다.");
        }
        return amazonS3Client.getUrl(bucket, filename).toString();
    }

    public S3Object getS3Object(String filename){
        return amazonS3Client.getObject(bucket, filename);
    }


    public String S3ImageURLConverter(String imageKey) {
        return "";
    }

}
