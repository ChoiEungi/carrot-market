package numble.carrotmarket.domain.user.application;

import lombok.RequiredArgsConstructor;
import numble.carrotmarket.domain.auth.JWTProvider;
import numble.carrotmarket.domain.exception.RegistrationException;
import numble.carrotmarket.domain.user.UserRepositroy;
import numble.carrotmarket.domain.exception.CustomException;
import numble.carrotmarket.domain.s3api.S3ApiProvider;
import numble.carrotmarket.domain.user.User;
import numble.carrotmarket.domain.user.dto.SignUpRequest;
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
    private final S3ApiProvider s3ApiProvider;
    private final JWTProvider jwtProvider;
    private long accessTokenDurationTime = 10 * 60 * 1000;

    @Transactional
    public Long createUser(SignUpRequest signUpRequest) {

        if(userRepositroy.existsByUserEmail(signUpRequest.getUserEmail())){
            throw new RegistrationException("이미 존재하는 이메일입니다.");
        }

        if(userRepositroy.existsByUserNickname(signUpRequest.getUserNickname())){
            throw new RegistrationException("이미 존재하는 닉네임입니다.");
        }

        User user = userRepositroy.save(new User(
                signUpRequest.getUserEmail(),
                signUpRequest.getUserName(),
                bCryptPasswordEncoder.encode(signUpRequest.getUserPassword()),
                signUpRequest.getUserPhoneNumber(),
                signUpRequest.getUserNickname()
        ));
        return user.getId();
    }

    public Cookie signIn(String userEmail, String userPassword) {
        User user = userRepositroy.findByUserEmail(userEmail)
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
        String fileUrl = s3ApiProvider.uploadFile(multipartFile, otherImageUrl);
    }


}
