package ua.softserve.ita.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ua.softserve.ita.model.enumtype.Period;
import ua.softserve.ita.model.enumtype.PostgreSQLEnumType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "job")
@TypeDef(
        name = "period",
        typeClass = PostgreSQLEnumType.class
)
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "position",nullable = false,length = 40)
    private String position;

    @Enumerated(EnumType.STRING)
    @Type(type = "period")
    @Column(name = "period",nullable = false)
    private Period period;

    @Column(name = "description",length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
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
        Job job = (Job) o;
        return jobId.equals(job.jobId) &&
                position.equals(job.position) &&
                period == job.period &&
                Objects.equals(description, job.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, position, period, description);
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", position='" + position + '\'' +
                ", period=" + period +
                ", description='" + description + '\'' +
                '}';
    }

}
