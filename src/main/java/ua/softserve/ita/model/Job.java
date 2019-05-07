package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.softserve.ita.adapter.LocalDateAdapter;
import ua.softserve.ita.adapter.LocalDateDeserializer;
import ua.softserve.ita.adapter.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "job")
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "position", nullable = false, length = 40)
    @NotNull(message = "position must be not null")
    @NotBlank(message = "position must be not blank")
    @Size(min = 3, max = 40, message = "position length is incorrect")
    private String position;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "begin")
    private LocalDate begin;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "\"end\"")
    private LocalDate end;

    @Column(name = "companyName", length = 50)
    @Size(min = 3, max = 50, message = "company name length is incorrect")
    private String companyName;

    @Column(name = "description", length = 200)
    @Size(min = 3, max = 200, message = "description length is incorrect")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cv_id", nullable = false)
    @NotNull(message = "cv must be not null")
    private CV cv;

    public CV getCv() {
        return cv;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }

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

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
                Objects.equals(begin, job.begin) &&
                Objects.equals(end, job.end) &&
                Objects.equals(companyName, job.companyName) &&
                Objects.equals(description, job.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, position, begin, end, companyName, description);
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", position='" + position + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", companyName='" + companyName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
