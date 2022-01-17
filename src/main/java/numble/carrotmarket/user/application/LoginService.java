package numble.carrotmarket.user.application;

import numble.carrotmarket.user.dto.LoginRequest;

public interface LoginService {
    void login(LoginRequest request);

    void logout();

    String getLoginUser();
}
