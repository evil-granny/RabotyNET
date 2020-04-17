package ua.com.service;

import ua.com.dto.search.SearchRequestDto;

public interface SearchService<Z> {

    Z getResponse(SearchRequestDto searchRequestDto);

}
