package ua.softserve.ita.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "education")
public class Education implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long educationId;

    @Column(name = "degree", nullable = false, length = 30)
    private String degree;

    @Column(name = "school", nullable = false, length = 50)
    private String school;

    @Column(name = "specialty", length = 100)
    private String specialty;

    @Column(name = "graduation")
    private Integer graduation;

    @OneToOne(mappedBy = "education")
    private CV cv;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return educationId.equals(education.educationId) &&
                degree.equals(education.degree) &&
                school.equals(education.school) &&
                Objects.equals(specialty, education.specialty) &&
                Objects.equals(graduation, education.graduation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(educationId, degree, school, specialty, graduation);
    }

    @Override
    public String toString() {
        return "Education{" +
                "educationId=" + educationId +
                ", degree='" + degree + '\'' +
                ", school='" + school + '\'' +
                ", specialty='" + specialty + '\'' +
                ", graduation=" + graduation +
                '}';
    }

}
