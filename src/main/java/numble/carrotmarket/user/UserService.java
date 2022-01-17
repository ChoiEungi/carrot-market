package numble.carrotmarket.user;

import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import numble.carrotmarket.exception.CustomException;
import numble.carrotmarket.s3api.S3ApiService;
import numble.carrotmarket.user.dto.SignUpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositroy userRepositroy;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final S3ApiService s3ApiService;

    @Transactional
    public Long createUser(SignUpRequest signUpRequest) {
        User user = userRepositroy.save(new User(
                signUpRequest.getUserName(),
                bCryptPasswordEncoder.encode(signUpRequest.getUserPassword()),
                signUpRequest.getUserPhoneNumber(),
                signUpRequest.getUserNickname()
        ));
        return user.getUserId();
    }

    public void signIn(String username, String userPassword) {
        User user = userRepositroy.findByUserName(username)
                .orElseThrow(CustomException::new);
        if (!bCryptPasswordEncoder.matches(userPassword, user.getUserPassword())) {
            throw new CustomException("비밀번호 일치하지 않습니다.");
        }
        // create Session ? JWT?
    }

    public void changeUserImage(MultipartFile multipartFile, String otherImageUrl) throws IOException {
        String fileUrl = s3ApiService.uploadFile(multipartFile, otherImageUrl);


    }



}
