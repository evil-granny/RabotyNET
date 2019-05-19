package ua.softserve.ita.dto.VacancyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.softserve.ita.model.Vacancy;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDTO {
    private Long count;
    private List<Vacancy> vacancies;
}
