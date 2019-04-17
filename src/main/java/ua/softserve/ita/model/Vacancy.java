package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ua.softserve.ita.model.enumtype.TypeOfEmployment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancy_id")
    private Long vacancyId;

    @Column(name = "position", nullable = false, length = 40)
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_employment")
    private TypeOfEmployment typeOfEmployment;

   /* //@JsonIgnore
    @Column(name = "requirements", nullable = false, columnDefinition = "character varying []", length = 200)
    //@ElementCollection(targetClass=String.class)
    private List<String> requirements;*/

    @Column(name = "salary")
    private Integer salary;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false,insertable =false,updatable = false)
    private Company company;

    @Column(name = "company_id")
    private Long companyId;

    @JsonIgnore
    @OneToMany(mappedBy = "vacancy")
    //@Column(insertable = false,updatable = false)
    //@JoinColumn(name = "requirement_id", insertable = false, updatable = false)
    private List<Requirement> requirements;


//
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
//
    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TypeOfEmployment getTypeOfEmployment() {
        return typeOfEmployment;
    }

    public void setTypeOfEmployment(TypeOfEmployment typeOfEmployment) {
        this.typeOfEmployment = typeOfEmployment;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return vacancyId.equals(vacancy.vacancyId) &&
                position.equals(vacancy.position) &&
                company.equals(vacancy.company) &&
                typeOfEmployment == vacancy.typeOfEmployment &&
                Objects.equals(salary, vacancy.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacancyId, position, typeOfEmployment, salary,company);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", position='" + position + '\'' +
                ", typeOfEmployment=" + typeOfEmployment +
                ", salary=" + salary +
                ", company=" + company +
                ", requirements=" + requirements +
                '}';
    }
}
