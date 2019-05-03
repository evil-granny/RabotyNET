package ua.softserve.ita.model;

import ua.softserve.ita.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

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
    @JoinColumn(name = "contacts_id", referencedColumnName = "contacts_id", nullable = false)
    private Contact contact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private Address address;

    @Column(name = "logo")
    private String logo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<Vacancy> vacancies;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "email_sent")
    private boolean emailSent;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public static boolean isValid(Company company) {
        return Validator.validate(company) && Validator.validate(company.address) && Validator.validate(company.contact);
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public User getUser() {
          return user;
     }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
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
                ", approved=" + approved +
                ", emailSent=" + emailSent +
                ", user=" + user +
                '}';
    }
}
