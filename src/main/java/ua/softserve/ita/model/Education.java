package ua.softserve.ita.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull(message = "degree must be not null")
    @NotBlank(message = "degree must be not blank")
    @Size(min = 3, max = 30, message = "degree length is incorrect")
    private String degree;

    @Column(name = "school", nullable = false, length = 50)
    @NotNull(message = "school must be not null")
    @NotBlank(message = "school must be not blank")
    @Size(min = 3, max = 50, message = "school length is incorrect")
    private String school;

    @Column(name = "specialty", length = 100)
    @Size(min = 3, max = 100, message = "name length is incorrect")
    private String specialty;

    @Column(name = "graduation")
    private Integer graduation;


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
