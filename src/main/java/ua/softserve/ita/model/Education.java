package ua.softserve.ita.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "education")
public class Education implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long educationId;

    @Column(name = "degree")
    private String degree;

    @Column(name = "school")
    private String school;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "graduation")
    private Integer graduation;

    @Column(name = "city")
    private String city;

    @OneToOne(mappedBy = "education")
    private CV cv;

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return educationId.equals(education.educationId) &&
                degree.equals(education.degree) &&
                school.equals(education.school) &&
                Objects.equals(specialty, education.specialty) &&
                Objects.equals(graduation, education.graduation) &&
                Objects.equals(city, education.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(educationId, degree, school, specialty, graduation, city);
    }

    @Override
    public String toString() {
        return "Education{" +
                "educationId=" + educationId +
                ", degree='" + degree + '\'' +
                ", school='" + school + '\'' +
                ", specialty='" + specialty + '\'' +
                ", graduation=" + graduation +
                ", city='" + city + '\'' +
                '}';
    }

}
