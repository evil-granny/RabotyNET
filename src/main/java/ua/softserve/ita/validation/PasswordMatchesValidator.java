package ua.softserve.ita.validation;

import ua.softserve.ita.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
   public void initialize(PasswordMatches constraint) {
   }

   public boolean isValid(Object obj, ConstraintValidatorContext context) {
      final User user = (User) obj;
      return user.getPassword().equals(user.getMatchingPassword());
   }
}
