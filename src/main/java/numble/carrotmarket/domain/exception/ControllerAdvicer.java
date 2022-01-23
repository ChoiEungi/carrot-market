package numble.carrotmarket.domain.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvicer {

    private final SpringTemplateEngine springTemplateEngine;

    @ExceptionHandler(CustomException.class)
    public String handle(CustomException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RegistrationException.class)
    public String handle(RegistrationException ex, Model model) {
        model.addAttribute("globalError", ex.getMessage());
        return RegistrationException.SIGN_UP_HTML;
    }

    @ExceptionHandler(IOException.class)
    public String handle(IOException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(JWTVerificationException.class)
    public String handle(JWTVerificationException ex){
        return ex.getMessage();
    }

}
