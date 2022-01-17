package numble.carrotmarket.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ControllerAdvicer {

    @ExceptionHandler(CustomException.class)
    public String handle(CustomException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IOException.class)
    public String handle(IOException ex) {
        return ex.getMessage();
    }

}
