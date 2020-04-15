package ua.com.dto.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.model.enumtype.Status;
import ua.com.model.profile.Address;
import ua.com.model.profile.Contact;
import ua.com.model.User;
import ua.com.model.Vacancy;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto {

    private Long companyId;

    private String name;

    private String edrpou;

    private String description;

    private String website;

    private Status status;

    private Contact contact;

    private Address address;

    private String logo;

    private Set<Vacancy> vacancies;

    private User user;

}
