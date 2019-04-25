package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.softserve.ita.model.enumtype.Employment;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancy_id")
    private Long vacancyId;

    @NotNull(message = "Position must be not null")
    @Max(40)
    @NotBlank(message = "position can't be blank")
    @Column(name = "position", nullable = false, length = 40)
    private String position;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "employment")
    private Employment employment;

    @Column(name = "salary")
    private Integer salary;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Requirement> requirements;


    public Vacancy() {
    }

    public Vacancy(Employment employment, Integer salary, Company company, Set<Requirement> requirements) {
        this.position = position;
        this.employment = employment;
        this.salary = salary;
        this.company = company;
        this.requirements = requirements;
    }

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

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
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

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<Requirement> requirements) {
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", position='" + position + '\'' +
                ", employment=" + employment +
                ", salary=" + salary +
                ", requirement=" + requirements +
                '}';
    }
}
