package ua.softserve.ita.dto;

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
public class VacancyDto {

    private Long count;

    private List<Vacancy> vacancies;

}
