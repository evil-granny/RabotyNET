package ua.com.dto.company;

import lombok.*;
import ua.com.model.Company;

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
