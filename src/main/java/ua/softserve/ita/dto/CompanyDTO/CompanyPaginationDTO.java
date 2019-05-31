package ua.softserve.ita.dto.CompanyDTO;

import lombok.*;
import ua.softserve.ita.model.Company;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CompanyPaginationDTO {

    private Long count;
    private List<Company> companies;

}
