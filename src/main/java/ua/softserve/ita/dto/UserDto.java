package ua.softserve.ita.dto;

import lombok.Data;
import ua.softserve.ita.validation.PasswordMatches;
import ua.softserve.ita.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
@Data
public class UserDto {

    @NotNull
    @NotEmpty
    @ValidEmail
    private String login;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 60)
    private String password;

    @NotNull
    private String matchingPassword;

}
