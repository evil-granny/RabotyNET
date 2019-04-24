package ua.softserve.ita.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.softserve.ita.model.User;

public class UserValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(final Object obj, final Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.password",
                "Password is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "message.username",
                "Login is required.");
    }

}