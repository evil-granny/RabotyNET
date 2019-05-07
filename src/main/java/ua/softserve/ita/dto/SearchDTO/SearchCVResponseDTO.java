package ua.softserve.ita.dto.SearchDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class SearchCVResponseDTO {

    private int count;

    private Set<SearchCVDTO> SearchCVDTOs;
}
