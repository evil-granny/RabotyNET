package ua.softserve.ita.dto.SearchDTO;

import lombok.Data;

@Data
public class SearchRequestDTO {

    private String searchText;

    private String searchParameter;

    private String searchDocument;

    private String searchSort;

    private String direction;

    private int resultsOnPage;

    private int firstResultNumber;
}
