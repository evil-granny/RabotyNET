package ua.softserve.ita.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Object already exists")
public class CompanyAlreadyExistException extends NullPointerException {

    private static final long serialVersionUID = 1L;

    public CompanyAlreadyExistException(String message) {
        super(message);
    }

}
