package ua.softserve.ita.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "edrpou", nullable = false)
    private Integer edrpou;

    @Column(name = "description")
    private String description;

    @Column(name = "website", length = 50)
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contacts_id", referencedColumnName = "contacts_id", nullable = false)
    private Contacts contacts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private Address address;

    @Column(name = "logo")
    private String logo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<Vacancy> vacancies;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;


    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", edrpou=" + edrpou +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", contacts=" + contacts +
                ", address=" + address +
                ", logo='" + logo + '\'' +
                '}';
    }

}
