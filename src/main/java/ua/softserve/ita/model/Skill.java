package ua.softserve.ita.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "skill")
public class Skill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "title",nullable = false,length = 30)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
