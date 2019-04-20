package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "edrpou", nullable = false, length = 30)
    private String edrpou;

    @Column(name = "description")
    private String description;

    @Column(name = "website", length = 50)
    private String website;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "contacts_id", referencedColumnName = "contacts_id", nullable = false)
//    private Contacts contacts;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
//    private Address address;

    @Column(name = "logo")
    private String logo;

//    @JsonIgnore
//    @OneToMany(mappedBy = "company")
//    private List<Vacancy> vacancies;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
//    private User user;


    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", edrpou='" + edrpou + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdrpou() {
        return edrpou;
    }

    public void setEdrpou(String edrpou) {
        this.edrpou = edrpou;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

//    public Contacts getContacts() {
//        return contacts;
//    }
//
//    public void setContacts(Contacts contacts) {
//        this.contacts = contacts;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public List<Vacancy> getVacancies() {
//        return vacancies;
//    }
//
//    public void setVacancies(List<Vacancy> vacancies) {
//        this.vacancies = vacancies;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Company company = (Company) o;
//        return companyId.equals(company.companyId) &&
//                name.equals(company.name) &&
//                edrpou.equals(company.edrpou) &&
//                Objects.equals(description, company.description) &&
//                Objects.equals(website, company.website) &&
//                contacts.equals(company.contacts) &&
//                address.equals(company.address) &&
//                Objects.equals(logo, company.logo) &&
//                Objects.equals(vacancies, company.vacancies);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(companyId, name, edrpou, description, website, contacts, address, logo, vacancies);
//    }
//
//    @Override
//    public String toString() {
//        return "Company{" +
//                "companyId=" + companyId +
//                ", name='" + name + '\'' +
//                ", edrpou=" + edrpou +
//                ", description='" + description + '\'' +
//                ", website='" + website + '\'' +
//                ", contacts=" + contacts +
//                ", address=" + address +
//                ", logo='" + logo + '\'' +
//                ", vacancies=" + vacancies +
//                '}';
//    }

}
