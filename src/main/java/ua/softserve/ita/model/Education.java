package ua.softserve.ita.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "education")
public class Education implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long educationId;

    @Column(name = "degree", nullable = false, length = 30)
    private String degree;

    @Column(name = "school", nullable = false, length = 50)
    private String school;

    @Column(name = "specialty", length = 100)
    private String specialty;

    @Column(name = "graduation")
    private Integer graduation;

    @OneToOne(mappedBy = "education")
    private CV cv;

}
