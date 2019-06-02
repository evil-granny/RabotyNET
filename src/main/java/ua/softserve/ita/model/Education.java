package ua.softserve.ita.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "education")
public class Education implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long educationId;

    @Column(name = "degree", nullable = false, length = 30)
    @NotNull(message = "degree must be not null")
    @NotBlank(message = "degree must be not blank")
    @Size(min = 3, max = 30, message = "degree length is incorrect")
    private String degree;

    @Column(name = "school", nullable = false, length = 50)
    @NotNull(message = "school must be not null")
    @NotBlank(message = "school must be not blank")
    @Size(min = 3, max = 50, message = "school length is incorrect")
    private String school;

    @Column(name = "specialty", length = 100)
    @Size(max = 100, message = "specialty length is incorrect")
    private String specialty;

    @Column(name = "graduation")
    private Integer graduation;

}
