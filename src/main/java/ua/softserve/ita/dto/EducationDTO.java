package ua.softserve.ita.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.softserve.ita.model.CV;

@Getter
@Setter
@NoArgsConstructor
public class EducationDTO {
    private Long educationId;
    private String degree;
    private String school;
    private String specialty;
    private Integer graduation;
    private CV cv;
}
