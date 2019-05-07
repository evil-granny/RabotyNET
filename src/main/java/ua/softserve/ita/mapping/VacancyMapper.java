package ua.softserve.ita.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.softserve.ita.dto.VacancyDTO;
import ua.softserve.ita.model.Vacancy;

@Mapper(componentModel = "spring")
public interface VacancyMapper extends BaseMapper<VacancyDTO, Vacancy> {
    @Override
    @Mapping(target = "vacancyId", source = "entity.vacancy.id")
    VacancyDTO toDTO(Vacancy vacancy);

}
