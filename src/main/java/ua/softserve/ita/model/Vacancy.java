package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.softserve.ita.model.enumtype.Employment;

import javax.persistence.*;

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
    @Column(name = "employment")
    private Employment employment;

    @Column(name = "salary")
    private Integer salary;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    //@JsonIgnore
    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Requirement> requirements;

    @Override
    public String toString() {
        return "Vacancy{" +
                "vacancyId=" + vacancyId +
                ", position='" + position + '\'' +
                ", employment=" + employment +
                ", salary=" + salary +
                '}';
    }
}
