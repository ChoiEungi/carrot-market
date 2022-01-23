package numble.carrotmarket.domain.exception;

public class RegistrationException extends CustomException{
    public static final String SIGN_UP_HTML = "registration-error.html";

    public RegistrationException() {
        super();
    }

    public RegistrationException(String message) {
        super(message);
    }
}
