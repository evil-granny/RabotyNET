package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.softserve.ita.model.enumtype.Employment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "vacancy")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = Vacancy.FIND_BY_COMPANY, query = "select vac from Vacancy vac where vac.company.name = :name"),
        @NamedQuery(name = Vacancy.FIND_BY_REQUIREMENT, query = "SELECT vac FROM Vacancy vac WHERE vac.vacancyId = (SELECT req.vacancy.vacancyId FROM Requirement req WHERE req.requirementId = :id)"),
        @NamedQuery(name = Vacancy.FIND_COUNT_VACANCY, query = "select count(vac.vacancyId) from Vacancy vac where vac.company.name = :name"),
        @NamedQuery(name = Vacancy.FIND_COUNT_All_VACANCY, query = "select count(vac.vacancyId) from Vacancy vac")
})
public class Vacancy {
    public static final String FIND_BY_COMPANY = "Vacancy.findByCompany";
    public static final String FIND_BY_REQUIREMENT = "Vacancy.findByRequirement";
    public static final String FIND_COUNT_VACANCY = "Vacancy.findCountVacancy";
    public static final String FIND_COUNT_All_VACANCY = "Vacancy.findCountAllVacancy";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancy_id")
    private Long vacancyId;

    @NotNull(message = "Description must be not null")
    @NotBlank(message = "Description can't be blank")
    @Column(name = "description", nullable = false, length = 60)
    @Pattern(regexp = "^[A-Za-z0-9\\s\\S]*")
    private String description;

    @NotNull(message = "Position must be not null")
    @NotBlank(message = "Position can't be blank")
    @Column(name = "position", nullable = false, length = 40)
    @Pattern(regexp = "^[a-zA-Z0-9_,.\\- &]*")
    private String position;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "employment")
    private Employment employment;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "hot_vacancy")
    private Boolean hotVacancy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Requirement> requirements;

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", position='" + position + '\'' +
                ", employment=" + employment +
                ", salary=" + salary +
                ", hot=" + hotVacancy +
                ", requirement=" + requirements +
                '}';
    }

}
