package ua.softserve.ita.model;

import lombok.*;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.model.profile.Contact;
import ua.softserve.ita.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name", nullable = false, length = 30)
    @NotNull(message = "name must be not null")
    @NotBlank(message = "name must be not blank")
    @Size(min = 3, max = 30, message = "name length is incorrect")
    private String name;

    @Column(name = "edrpou", nullable = false, length = 10)
    @NotNull(message = "edrpou must be not null")
    @NotBlank(message = "edrpou must be not blank")
    @Size(min = 8, max = 10, message = "edrpou length is incorrect")
    private String edrpou;

    @Column(name = "description", length = 2000)
    @Size(max = 2000, message = "description is too long")
    private String description;

    @Column(name = "website", nullable = false, length = 50)
    @NotNull(message = "website must be not null")
    @NotBlank(message = "website must be not blank")
    @Size(max = 50, message = "website url length is too long")
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id", nullable = false)
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private Address address;

    @Column(name = "logo")
    private String logo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<Vacancy> vacancies;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public static boolean isValid(Company company) {
        return Validator.validate(company) && Validator.validate(company.address) && Validator.validate(company.contact);
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", edrpou='" + edrpou + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", contact=" + contact +
                ", address=" + address +
                ", logo='" + logo + '\'' +
                ", vacancies=" + vacancies +
                ", user=" + user +
                '}';
    }
}
