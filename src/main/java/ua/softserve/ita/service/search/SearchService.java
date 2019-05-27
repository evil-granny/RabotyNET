package ua.softserve.ita.service.search;

import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;

public interface SearchService<Z> {

    Z getResponse(SearchRequestDTO searchRequestDTO);

}
