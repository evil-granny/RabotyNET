package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
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

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "print_pdf",nullable = false)
    private Boolean printPdf;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

}
