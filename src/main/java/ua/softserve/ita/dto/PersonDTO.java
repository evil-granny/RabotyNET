package ua.softserve.ita.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.model.profile.Contact;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String photo;
    private Contact contact;
    private Address address;
    private User user;
}
