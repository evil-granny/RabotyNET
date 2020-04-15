package ua.com.dto.search;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
public class SearchVacancyResponseDto {

    private BigInteger count;

    private List<SearchVacancyDto> searchVacancyDtos;

}
