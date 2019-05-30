package ua.softserve.ita.model;

import lombok.*;
import ua.softserve.ita.model.profile.Person;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "resume")
@NamedQueries({
        @NamedQuery(name = Resume.FIND_BY_USER_ID, query = "select rs from Resume rs where rs.person.userId = :id"),
})
public class Resume implements Serializable {
    public static final String FIND_BY_USER_ID = "Resume.findByUserId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long resumeId;

    @Column(name = "position", nullable = false, length = 50)
    @NotNull(message = "position must be not null")
    @NotBlank(message = "position must be not blank")
    @Size(min = 3, max = 50, message = "position length is incorrect")
    private String position;

    @OneToMany(mappedBy = "resume",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Skill> skills;

    @OneToMany(mappedBy = "resume",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Job> jobs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "education_id", referencedColumnName = "education_id", nullable = false)
    @NotNull(message = "education must be not null")
    private Education education;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull(message = "person must be not null")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Person person;

}
