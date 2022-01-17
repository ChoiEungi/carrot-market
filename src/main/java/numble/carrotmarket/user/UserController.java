package numble.carrotmarket.user;

import lombok.RequiredArgsConstructor;
import numble.carrotmarket.user.dto.LoginRequest;
import numble.carrotmarket.user.dto.SignUpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public void createUser(@RequestBody SignUpRequest signUpRequest) {
        userService.createUser(signUpRequest);
    }

    @PostMapping("/login")
    public void loginUser(@RequestBody LoginRequest loginRequest) {
        userService.signIn(loginRequest.getUsername(), loginRequest.getUserPassword());
    }

    @PutMapping("/me/image")
    public void changeUserImage(){

    }


}
