package ua.softserve.ita.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cv")
public class CV implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private Long cvId;

    @Column(name = "photo")
    private String photo;

    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "cv")
    private List<Skill> skills;

    @OneToMany(mappedBy = "cv")
    private List<Job> jobs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "education_id", referencedColumnName = "education_id")
    private Education education;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Person person;

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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
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
        return Objects.hash(cvId, photo, position, skills, jobs, education);
    }

}
