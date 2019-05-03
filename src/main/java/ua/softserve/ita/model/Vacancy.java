package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.softserve.ita.model.enumtype.Employment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "vacancy")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@NamedQuery(name = Vacancy.FIND_BY_COMPANY, query = "select vac from Vacancy vac where vac.company_id = :id")
public class Vacancy {
    //public static final String FIND_BY_COMPANY = "Vacancy.findByCompany";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancy_id")
    private Long vacancyId;

    @NotNull(message = "Position must be not null")
    @NotBlank(message = "Position can't be blank")
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

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", position='" + position + '\'' +
                ", employment=" + employment +
                ", salary=" + salary +
                ", requirement=" + requirements +
                //", company=" + company +
                '}';
    }
}
