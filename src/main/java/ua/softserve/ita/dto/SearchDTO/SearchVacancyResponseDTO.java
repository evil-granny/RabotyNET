package ua.softserve.ita.dto.SearchDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@ToString
public class SearchVacancyResponseDTO {

    private BigInteger count;

    private List<SearchVacancyDTO> searchVacancyDTOS;
}
