package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "skill")
public class Skill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "title", nullable = false, length = 30)
    @NotNull(message = "title must be not null")
    @NotBlank(message = "title must be not blank")
    @Size(min = 3, max = 30, message = "title length is incorrect")
    private String title;

    @Column(name = "description")
    @Size(max = 255, message = "description length is incorrect")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cv_id", nullable = false)
    @NotNull(message = "cv must be not null")
    private CV cv;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return skillId.equals(skill.skillId) &&
                title.equals(skill.title) &&
                Objects.equals(description, skill.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, title, description);
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillId=" + skillId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
