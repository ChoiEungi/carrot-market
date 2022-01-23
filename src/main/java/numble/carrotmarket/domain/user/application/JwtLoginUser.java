package numble.carrotmarket.domain.user.application;

import lombok.AllArgsConstructor;
import numble.carrotmarket.domain.user.dto.LoginRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class JwtLoginUser implements LoginService {

    private HttpSession httpSession;

    @Override
    public void login(LoginRequest request) {

    }

    @Override
    public void logout() {

    }

    @Override
    public String getLoginUser() {
        return null;
    }
}
