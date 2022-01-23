package numble.carrotmarket.user;

import numble.carrotmarket.domain.user.User;
import numble.carrotmarket.domain.user.UserRepositroy;
import numble.carrotmarket.domain.exception.CustomException;
import numble.carrotmarket.domain.user.application.UserService;
import numble.carrotmarket.domain.user.dto.SignUpRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceTest {

    public static final SignUpRequest SIGN_UP_REQUEST = new SignUpRequest("choieungi@gm.gist.ac.kr","eungi", "abc", "010-1111-1234", "grace_goose");
    public static final String RAW_PASSWORD = "abc";

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepositroy userRepositroy;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepositroy.save(new User("choieungi@gm.gist.ac.kr", "eungi", bCryptPasswordEncoder.encode(RAW_PASSWORD), "010-1111-1234", "grace_goose"));
    }

    @Test
    void createUser() {
        Long userId = userService.createUser(SIGN_UP_REQUEST);
        User user = userRepositroy.findById(userId).orElseThrow(IllegalArgumentException::new);
        assertThat(user.getUserEmail()).isEqualTo(SIGN_UP_REQUEST.getUserEmail());
        assertThat(user.getUserName()).isEqualTo(SIGN_UP_REQUEST.getUserName());
        assertTrue(bCryptPasswordEncoder.matches(SIGN_UP_REQUEST.getUserPassword(), user.getUserPassword()));
        assertThat(user.getUserPhoneNumber()).isEqualTo(SIGN_UP_REQUEST.getUserPhoneNumber());
        assertThat(user.getUserNickname()).isEqualTo(SIGN_UP_REQUEST.getUserNickname());
    }

    @Test
    void FailsignInNotExistUserName() {
        String notExistUserName = user.getUserName() + "invalid";
        assertThrows(CustomException.class, () -> userService.signIn(notExistUserName, user.getUserPassword()));
    }

    @Test
    void FailsignInIncorrectPassword() {
        String incorrectPassword = RAW_PASSWORD + "incorrect";
        assertThrows(CustomException.class, () -> userService.signIn(user.getUserName(), incorrectPassword));
    }

    @Test
    void singInTest() {
        userService.signIn(user.getUserName(), RAW_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        userRepositroy.deleteAllInBatch();
    }
}