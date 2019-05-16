package ua.softserve.ita.dto.VacancyDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.softserve.ita.model.Company;
import ua.softserve.ita.model.Requirement;
import ua.softserve.ita.model.enumtype.Employment;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class VacancyDTO {
    private Long vacancyId;
    private String description;
    private String position;
    private Employment employment;
    private Integer salary;
    private Company company;
    private Set<Requirement> requirements;
}
