package numble.carrotmarket.web;

import lombok.RequiredArgsConstructor;
import numble.carrotmarket.domain.auth.argumentresolver.LoginUser;
import numble.carrotmarket.domain.product.ProductService;
import numble.carrotmarket.domain.user.application.UserService;
import numble.carrotmarket.domain.user.dto.LoginRequest;
import numble.carrotmarket.domain.user.dto.SignUpRequest;
import numble.carrotmarket.domain.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public String createUser(@Validated @ModelAttribute("signUpRequest") SignUpRequest signUpRequest, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("result = " + result);
            return "sign-up";
        }
        userService.createUser(signUpRequest);
        return "/";
    }

    @GetMapping("/sign-up")
    public String signUpPage(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "sign-up";
    }

    @PostMapping("/login")
    public String loginUser(@Validated @ModelAttribute("loginRequest") LoginRequest loginRequest, HttpServletResponse response) {
        Cookie cookie = userService.signIn(loginRequest.getUserEmail(), loginRequest.getUserPassword());
        response.addCookie(cookie);
        return "redirect:/products";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
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


    @Controller
    @RequiredArgsConstructor
    public static class ProductController {
        private final ProductService productService;

        @GetMapping("/products")
        public String getAllProducts(Model model) {
            model.addAttribute("products", productService.findAllProducts());
            return "all-product-view";
        }

    }
}
