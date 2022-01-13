package numble.carrotmarket.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }
}
