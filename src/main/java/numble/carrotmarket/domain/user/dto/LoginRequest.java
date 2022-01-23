package numble.carrotmarket.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Setter
public class LoginRequest {

    @Email
    @NotBlank
    private String userEmail;

    @NotBlank
    private String userPassword;

    public LoginRequest() {
    }
}
