package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    public String toString() {
        return "Requirement{" +
                "requirementId=" + requirementId +
                ", description='" + description + '\'' +
                '}';
    }

}
