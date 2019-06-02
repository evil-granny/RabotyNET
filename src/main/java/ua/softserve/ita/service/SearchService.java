package ua.softserve.ita.service;

import ua.softserve.ita.dto.search.SearchRequestDto;

public interface SearchService<Z> {

    Z getResponse(SearchRequestDto searchRequestDto);

}
