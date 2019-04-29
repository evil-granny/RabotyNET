package ua.softserve.ita.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Validator {

    private static Logger log = Logger.getLogger(Validator.class.getName());

    public static boolean validate(Object object) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = vf.getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(object);

        if(constraintViolations.size() > 0) {
            log.log(Level.WARNING, String.format("%d Errors found!",
                    constraintViolations.size()));

            for (ConstraintViolation<Object> cv : constraintViolations)
                log.log(Level.WARNING, String.format(
                        "An error found! property: [%s], value: [%s], message: [%s]",
                        cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
        }

        return constraintViolations.size() == 0;
    }
}
