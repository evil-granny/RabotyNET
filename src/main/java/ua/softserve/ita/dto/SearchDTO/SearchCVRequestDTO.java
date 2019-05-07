package ua.softserve.ita.dto.SearchDTO;

import lombok.Data;

@Data
public class SearchCVRequestDTO {

    private String searchText;

    private String searchParameter;

    private String searchDocument;

    private int resultsOnPage;

    private int firstResultNumber;
}
