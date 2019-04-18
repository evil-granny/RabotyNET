package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "requirement")
@Getter
@Setter
@NoArgsConstructor
public class Requirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id")
    private Long requirementId;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", nullable = false/*,insertable = false,updatable = false*/)//
    private Vacancy vacancy;

   /* @Column(name = "vacancy_id")
    private Long vacancyId;*/

    public Requirement(String description, Vacancy vacancy) {
        this.description = description;
        this.vacancy = vacancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirement that = (Requirement) o;
        return Objects.equals(requirementId, that.requirementId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(vacancy, that.vacancy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirementId, description, vacancy);
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "requirementId=" + requirementId +
                ", description='" + description + '\'' +
                //", vacancy=" + vacancy +
                '}';
    }
}
