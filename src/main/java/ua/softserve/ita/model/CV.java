package ua.softserve.ita.model;

import lombok.Data;
import ua.softserve.ita.model.profile.Person;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
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
    @NotNull(message = "position must be not null")
    @NotBlank(message = "position must be not blank")
    @Size(min = 3, max = 50, message = "position length is incorrect")
    private String position;

    @OneToMany(mappedBy = "cv",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Skill> skills;

    @OneToMany(mappedBy = "cv",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Job> jobs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "education_id", referencedColumnName = "education_id", nullable = false)
    @NotNull(message = "education must be not null")
    private Education education;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Person person;

}
