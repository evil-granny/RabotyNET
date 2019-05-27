package ua.softserve.ita.dto.SearchDTO;

import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
public class SearchVacancyResponseDTO {

    private BigInteger count;

    private List<SearchVacancyDTO> searchVacancyDTOS;
}
