package numble.carrotmarket.user;

import lombok.RequiredArgsConstructor;
import numble.carrotmarket.auth.argumentresolver.LoginUser;
import numble.carrotmarket.exception.CustomException;
import numble.carrotmarket.user.application.UserService;
import numble.carrotmarket.user.dto.LoginRequest;
import numble.carrotmarket.user.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public void createUser(@RequestBody SignUpRequest signUpRequest) {
        userService.createUser(signUpRequest);
    }

    @PostMapping("/login")
    public void loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Cookie cookie = userService.signIn(loginRequest.getUsername(), loginRequest.getUserPassword());
        response.addCookie(cookie);
        // redirect
    }

    @GetMapping("/logout")
    public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new CustomException("토큰이 존재하지 않습니다.");
        }
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            response.addCookie(cookie);
        }

        // redirect
    }

    @GetMapping("/test")
    public ResponseEntity<String> testUser(@LoginUser String email) {
        return ResponseEntity.ok(email);
    }


    @PutMapping("/me/image")
    public void changeUserImage() {

    }


}
