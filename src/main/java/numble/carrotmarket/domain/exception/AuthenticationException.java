package numble.carrotmarket.domain.exception;

public class AuthenticationException extends CustomException{
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
