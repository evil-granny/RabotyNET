package ua.softserve.ita.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cv")
public class CV implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private Long cvId;

    @Column(name = "photo")
    private String photo;

    @Column(name = "position", nullable = false, length = 50)
    private String position;

    @OneToMany(mappedBy = "cv",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Skill> skills;

    @OneToMany(mappedBy = "cv",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Job> jobs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "education_id", referencedColumnName = "education_id", nullable = false)
    private Education education;

    public Long getCvId() {
        return cvId;
    }

    public void setCvId(Long cvId) {
        this.cvId = cvId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CV cv = (CV) o;
        return Objects.equals(cvId, cv.cvId) &&
                photo.equals(cv.photo) &&
                Objects.equals(position, cv.position) &&
                Objects.equals(skills, cv.skills) &&
                Objects.equals(jobs, cv.jobs) &&
                Objects.equals(education, cv.education);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cvId, photo, position, skills, education);
    }

    @Override
    public String toString() {
        return "CV{" +
                "cvId=" + cvId +
                ", photo='" + photo + '\'' +
                ", position='" + position + '\'' +
                ", skills=" + skills +
                ", jobs=" + jobs +
                ", education=" + education +
                //", person=" + person +
                '}';
    }

    public CV(){

    }

    public CV(String photo, String position, Set<Skill> skills, Set<Job> jobs, Education education) {
        this.photo = photo;
        this.position = position;
        this.skills = skills;
        this.jobs = jobs;
        this.education = education;
    }
}
