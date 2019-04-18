package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ua.softserve.ita.model.enumtype.TypeOfEmployment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vacancy")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancy_id")
    private Long vacancyId;

    @Column(name = "position", nullable = false, length = 40)
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_employment")
    private TypeOfEmployment typeOfEmployment;

    @Column(name = "salary")
    private Integer salary;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false/*,insertable =false,updatable = false*/)
    private Company company;

    /*@Column(name = "company_id")
    private Long companyId;*/

    //@JsonIgnore
    @OneToMany(mappedBy = "vacancy", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    //@Column(insertable = false,updatable = false)
    //@JoinColumn(name = "requirement_id", insertable = false, updatable = false)
    private Set<Requirement> requirements;



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
        return Objects.hash(vacancyId, position, typeOfEmployment, salary, company);
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", position='" + position + '\'' +
                ", typeOfEmployment=" + typeOfEmployment +
                ", salary=" + salary +
               /* ", company=" + company +
                ", requirements=" + requirements +*/
                '}';
    }
}
