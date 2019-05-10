package ua.softserve.ita.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cv")
public class CV implements Serializable {

    @Id
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

}
