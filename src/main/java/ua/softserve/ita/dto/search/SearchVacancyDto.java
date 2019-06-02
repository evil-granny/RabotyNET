package ua.softserve.ita.dto.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SearchVacancyDto {

    private long vacancyId;

    private long companyId;

    private String position;

    private String companyName;

    private String city;

    private String employment;

    private String salary;

}
