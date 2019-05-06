package ua.softserve.ita.dto;

import lombok.Data;

@Data
public class SearchDTO {

    private String searchText;

    private String searchParameter;

    private String searchDocument;

    private int resultsOnPage;

    private int firstResultNumber;
}
