package ua.softserve.ita.dto.company;

import lombok.*;
import ua.softserve.ita.model.Company;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CompanyPaginationDto {

    private Long count;

    private List<Company> companies;

}
