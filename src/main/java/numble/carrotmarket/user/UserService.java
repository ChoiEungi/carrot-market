package numble.carrotmarket.user;

import numble.carrotmarket.exception.CustomException;
import numble.carrotmarket.user.dto.SignUpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepositroy userRepositroy;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepositroy userRepositroy, BCryptPasswordEncoder bCryptPasswordEncode) {
        this.userRepositroy = userRepositroy;
        this.bCryptPasswordEncoder = bCryptPasswordEncode;
    }

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


}
