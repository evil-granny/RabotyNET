package ua.softserve.ita.service;

import ua.softserve.ita.dto.SearchDTO.SearchRequestDTO;

public interface SearchService<Z> {

    Z getResponse(SearchRequestDTO searchRequestDTO);

}
