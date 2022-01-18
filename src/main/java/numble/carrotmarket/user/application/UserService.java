package numble.carrotmarket.user.application;

import lombok.RequiredArgsConstructor;
import numble.carrotmarket.auth.JWTProvider;
import numble.carrotmarket.exception.CustomException;
import numble.carrotmarket.s3api.S3ApiService;
import numble.carrotmarket.user.User;
import numble.carrotmarket.user.UserRepositroy;
import numble.carrotmarket.user.dto.SignUpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositroy userRepositroy;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final S3ApiService s3ApiService;
    private final JWTProvider jwtProvider;
    private long accessTokenDurationTime = 10 * 60 * 1000;

    @Transactional
    public Long createUser(SignUpRequest signUpRequest) {
        User user = userRepositroy.save(new User(
                signUpRequest.getUserEmail(),
                signUpRequest.getUserName(),
                bCryptPasswordEncoder.encode(signUpRequest.getUserPassword()),
                signUpRequest.getUserPhoneNumber(),
                signUpRequest.getUserNickname()
        ));
        return user.getId();
    }

    public Cookie signIn(String username, String userPassword) {
        User user = userRepositroy.findByUserName(username)
                .orElseThrow(CustomException::new);
        if (!bCryptPasswordEncoder.matches(userPassword, user.getUserPassword())) {
            throw new CustomException("비밀번호 일치하지 않습니다.");
        }
        String accessToken = jwtProvider.createAccessToken(user.getUserEmail());
        Cookie cookie = new Cookie("access_token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(Long.valueOf(accessTokenDurationTime).intValue());
        return cookie;
    }

    public void changeUserImage(MultipartFile multipartFile, String otherImageUrl) throws IOException {
        String fileUrl = s3ApiService.uploadFile(multipartFile, otherImageUrl);


    }


}
