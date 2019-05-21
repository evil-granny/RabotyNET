package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "requirement")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id")
    private Long requirementId;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_,.\\- &]*")
    @NotBlank(message = "description can't be blank")
    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vacancy_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Vacancy vacancy;

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
                '}';
    }
}
