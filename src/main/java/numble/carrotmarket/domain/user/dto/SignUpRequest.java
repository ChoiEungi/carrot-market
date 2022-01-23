package numble.carrotmarket.domain.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SignUpRequest {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    @Min(value = 9 , message ="비밀번호는 최소 9자리 이상이여야 합니다.")
    private String userPassword;

    @NotBlank(message = "공백")
    private String userName;

    @NotBlank
    private String userPhoneNumber;

    @NotBlank
    private String userNickname;
}

