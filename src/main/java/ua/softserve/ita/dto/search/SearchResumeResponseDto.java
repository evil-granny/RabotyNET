package ua.softserve.ita.dto.search;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Builder
@Data
public class SearchResumeResponseDto {

    private BigInteger count;

    private List<SearchResumeDto> searchResumeDtos;

}
