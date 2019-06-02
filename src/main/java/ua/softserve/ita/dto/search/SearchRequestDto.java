package ua.softserve.ita.dto.search;

import lombok.Data;

@Data
public class SearchRequestDto {

    private String searchText;

    private String searchParameter;

    private String searchDocument;

    private String searchSort;

    private String direction;

    private int resultsOnPage;

    private int firstResultNumber;

}
