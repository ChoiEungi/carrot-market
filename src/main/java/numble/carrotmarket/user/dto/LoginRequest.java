package numble.carrotmarket.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String userPassword;

    public LoginRequest() {
    }
}
