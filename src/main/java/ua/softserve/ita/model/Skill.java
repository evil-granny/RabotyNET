package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Size(min = 1, max = 30, message = "title length is incorrect")
    private String title;

    @Column(name = "description")
    @Size(max = 255, message = "description length is incorrect")
    private String description;

    @Column(name = "print_pdf", nullable = false)
    private Boolean printPdf;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

}
