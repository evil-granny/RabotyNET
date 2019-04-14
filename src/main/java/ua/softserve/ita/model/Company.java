package ua.softserve.ita.model;

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

    @Column(name = "name",nullable = false,length = 30)
    private String name;

    @Column(name = "edrpou",nullable = false)
    private Integer edrpou;

    @Column(name = "description")
    private String description;

    @Column(name = "website",length = 50)
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id",nullable = false)
    private Address address;

    @Column(name = "email",nullable = false,length = 30)
    private String email;

    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "company")
    private List<Vacancy> vacancies;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",nullable = false)
    private User user;

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

    public Integer getEdrpou() {
        return edrpou;
    }

    public void setEdrpou(Integer edrpou) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId.equals(company.companyId) &&
                name.equals(company.name) &&
                edrpou.equals(company.edrpou) &&
                Objects.equals(description, company.description) &&
                Objects.equals(website, company.website) &&
                address.equals(company.address) &&
                email.equals(company.email) &&
                Objects.equals(logo, company.logo) &&
                Objects.equals(vacancies, company.vacancies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, name, edrpou, description, website, address, email, logo, vacancies);
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", edrpou=" + edrpou +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", logo='" + logo + '\'' +
                ", vacancies=" + vacancies +
                '}';
    }

}
