package numble.carrotmarket.domain.user.application;

import numble.carrotmarket.domain.user.dto.LoginRequest;

public interface LoginService {
    void login(LoginRequest request);

    void logout();

    String getLoginUser();
}
