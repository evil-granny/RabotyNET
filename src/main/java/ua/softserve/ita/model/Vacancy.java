package ua.softserve.ita.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ua.softserve.ita.model.enumtype.PostgreSQLEnumType;
import ua.softserve.ita.model.enumtype.TypeOfEmployment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
@TypeDef(
        name = "typeOfEmployment",
        typeClass = PostgreSQLEnumType.class
)
public class Vacancy implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "vacancy_id")
    private Long vacancyId;

    @Column(name = "position",nullable = false,length = 40)
    private String position;

    @Enumerated(EnumType.STRING)
    @Type(type = "typeOfEmployment")
    @Column(name = "type_of_employment")
    private TypeOfEmployment typeOfEmployment;

    @Column(name = "requirements",nullable = false,columnDefinition = "character varying []",length = 200)
    private ArrayList<String> requirements;

    @Column(name = "salary")
    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

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

    public ArrayList<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<String> requirements) {
        this.requirements = requirements;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return vacancyId.equals(vacancy.vacancyId) &&
                position.equals(vacancy.position) &&
                typeOfEmployment == vacancy.typeOfEmployment &&
                requirements.equals(vacancy.requirements) &&
                Objects.equals(salary, vacancy.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacancyId, position, typeOfEmployment, requirements, salary);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", position='" + position + '\'' +
                ", typeOfEmployment=" + typeOfEmployment +
                ", requirements=" + requirements +
                ", salary=" + salary +
                '}';
    }

}
