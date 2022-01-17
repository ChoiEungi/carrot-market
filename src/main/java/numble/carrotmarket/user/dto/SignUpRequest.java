package numble.carrotmarket.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String userEmail;
    private String userName;
    private String userPassword;
    private String userPhoneNumber;
    private String userNickname;
}
