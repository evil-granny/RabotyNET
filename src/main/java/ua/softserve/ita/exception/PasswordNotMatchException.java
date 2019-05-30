package ua.softserve.ita.exception;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException(final String message) {
        super(message);
    }

}
