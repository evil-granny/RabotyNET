package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.softserve.ita.model.enumtype.Currency;
import ua.softserve.ita.model.enumtype.Employment;
import ua.softserve.ita.model.enumtype.VacancyStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vacancy")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = Vacancy.FIND_VACANCIES_BY_COMPANY_ID, query = "select vac from Vacancy vac where vac.company.companyId = :id ORDER BY vac.vacancyId DESC"),
        @NamedQuery(name = Vacancy.FIND_ALL_HOT_VACANCIES, query = "select vac from Vacancy vac where vac.company.status = ua.softserve.ita.model.enumtype.Status.APPROVED and vac.hotVacancy = true ORDER BY vac.vacancyId DESC"),
        @NamedQuery(name = Vacancy.FIND_CLOSED_VACANCIES, query = "select vac from Vacancy vac where (vac.vacancyStatus = ua.softserve.ita.model.enumtype.VacancyStatus.OUTDATED or vac.vacancyStatus = ua.softserve.ita.model.enumtype.VacancyStatus.OCCUPIED) and vac.company.status = ua.softserve.ita.model.enumtype.Status.APPROVED and vac.company.user.userId = :id order by vac.vacancyId DESC"),
        @NamedQuery(name = Vacancy.FIND_VACANCIES, query = "select vac from Vacancy vac WHERE vac.company.status = ua.softserve.ita.model.enumtype.Status.APPROVED and vac.vacancyStatus = ua.softserve.ita.model.enumtype.VacancyStatus.OPEN ORDER BY vac.vacancyId DESC"),
        @NamedQuery(name = Vacancy.FIND_BY_REQUIREMENT, query = "SELECT vac FROM Vacancy vac WHERE vac.vacancyId = (SELECT req.vacancy.vacancyId FROM Requirement req WHERE req.requirementId = :id)"),
        @NamedQuery(name = Vacancy.FIND_COUNT_VACANCIES_BY_COMPANY_ID, query = "select count(vac.vacancyId) from Vacancy vac where vac.company.companyId = :id"),
        @NamedQuery(name = Vacancy.FIND_COUNT_All_VACANCY, query = "select count(vac.vacancyId) from Vacancy vac WHERE vac.company.status = ua.softserve.ita.model.enumtype.Status.APPROVED and vac.vacancyStatus = ua.softserve.ita.model.enumtype.VacancyStatus.OPEN"),
        @NamedQuery(name = Vacancy.FIND_COUNT_HOT_VACANCIES, query = "select count(vac.vacancyId) from Vacancy vac where vac.hotVacancy = true"),
        @NamedQuery(name = Vacancy.FIND_BY_VACANCY_ID, query = "select vac from Vacancy vac where vac.vacancyId = :id"),
        @NamedQuery(name = Vacancy.FIND_COUNT_CLOSED_VACANCIES, query = "select count(vac.vacancyId) from Vacancy vac where vac.vacancyStatus = ua.softserve.ita.model.enumtype.VacancyStatus.OUTDATED or vac.vacancyStatus = ua.softserve.ita.model.enumtype.VacancyStatus.OCCUPIED"),
})
public class Vacancy {

    public static final String FIND_VACANCIES_BY_COMPANY_ID = "Vacancy.findVacanciesByCompanyId";
    public static final String FIND_BY_REQUIREMENT = "Vacancy.findByRequirement";
    public static final String FIND_COUNT_VACANCIES_BY_COMPANY_ID = "Vacancy.findCountVacanciesByCompanyId";
    public static final String FIND_COUNT_All_VACANCY = "Vacancy.findCountAllVacancy";
    public static final String FIND_COUNT_HOT_VACANCIES = "Vacancy.findCountAllHotVacancies";
    public static final String FIND_COUNT_CLOSED_VACANCIES = "Vacancy.findCountAllClosedVacancies";
    public static final String FIND_VACANCIES = "Vacancy.findVacancies";
    public static final String FIND_ALL_HOT_VACANCIES = "Vacancy.findAllHotVacancies";
    public static final String FIND_BY_VACANCY_ID = "Vacancy.findVacancyByVacancyId";
    public static final String FIND_CLOSED_VACANCIES = "Vacancy.findAllClosedVacancies";

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vacancy_status")
    private VacancyStatus vacancyStatus;

    @Column(name = "salary")
    private Integer salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "hot_vacancy", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean hotVacancy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Requirement> requirements;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "vacancy_resume", joinColumns = {@JoinColumn(name = "vacancy_id")},
            inverseJoinColumns = {@JoinColumn(name = "resume_id")})
    private Set<Resume> resumes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(vacancyId, vacancy.vacancyId) &&
                Objects.equals(description, vacancy.description) &&
                Objects.equals(position, vacancy.position) &&
                employment == vacancy.employment &&
                vacancyStatus == vacancy.vacancyStatus &&
                Objects.equals(salary, vacancy.salary) &&
                currency == vacancy.currency &&
                Objects.equals(hotVacancy, vacancy.hotVacancy) &&
                Objects.equals(requirements, vacancy.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vacancyId, description, position, employment, vacancyStatus, salary, currency, hotVacancy, requirements);
    }

}
