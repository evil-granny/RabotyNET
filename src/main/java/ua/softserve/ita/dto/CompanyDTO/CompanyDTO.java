package ua.softserve.ita.dto.CompanyDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.softserve.ita.model.Status;
import ua.softserve.ita.model.User;
import ua.softserve.ita.model.Vacancy;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.model.profile.Contact;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO {
    private Long companyId;
    private String name;
    private String edrpou;
    private String description;
    private String website;
    private Contact contact;
    private Address address;
    private String logo;
    private Set<Vacancy> vacancies;
    private Status status;
    private User user;

}
