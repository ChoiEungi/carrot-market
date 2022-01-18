package numble.carrotmarket.exception;

public class AuthenticationException extends CustomException{
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
