package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ua.softserve.ita.model.profile.Address;
import ua.softserve.ita.model.profile.Contact;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@NamedQueries({
        @NamedQuery(name = Company.FIND_BY_VACANCY_ID,query = "SELECT com FROM Company com WHERE com.companyId = (SELECT vac.company.companyId FROM Vacancy vac WHERE vac.vacancyId = :id)"),
        @NamedQuery(name = Company.FIND_COUNT_COMPANY, query = "select count(com.companyId) from Company com"),
        @NamedQuery(name = Company.FIND_BY_COMPANY_NAME, query = "select com from Company com where com.name = :name"),
})
public class Company implements Serializable {
    public static final String FIND_BY_VACANCY_ID = "Company.findByVacancyId";
    public static final String FIND_COUNT_COMPANY= "Company.findCount";
    public static final String FIND_BY_COMPANY_NAME = "Company.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name", nullable = false, length = 30)
    @NotNull(message = "name must be not null")
    @NotBlank(message = "name must be not blank")
    @Size(min = 3, max = 30, message = "name length is incorrect")
    @Pattern(regexp = "^[A-Za-z0-9? ?A-Za-z0-9]{3,30}$")
    private String name;

    @Column(name = "edrpou", nullable = false, length = 10)
    @NotNull(message = "edrpou must be not null")
    @NotBlank(message = "edrpou must be not blank")
    @Size(min = 8, max = 10, message = "edrpou length is incorrect")
    @Pattern(regexp = "^[0-9]{8,10}$")
    private String edrpou;

    @Column(name = "description", length = 2000)
    @Size(max = 2000, message = "description is too long")
    @Pattern(regexp = "^[\\s\\S]{0,2000}$")
    private String description;

    @Column(name = "website", nullable = false, length = 50)
    @NotNull(message = "website must be not null")
    @NotBlank(message = "website must be not blank")
    @Size(max = 50, message = "website url length is too long")
    @Pattern(regexp = "^(?:http(s)?:\\/\\/)?[\\w.-]+(?:\\.[\\w\\.-]+)+[\\w\\-\\._~:/?#[\\\\]@!\\$&'\\(\\)\\*\\+,;=.]+$")
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id", nullable = false)
    @NotNull
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @NotNull
    private Address address;

    @Column(name = "logo")
    private String logo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<Vacancy> vacancies;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<Claim> claims;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @NotNull
    private User user;

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
