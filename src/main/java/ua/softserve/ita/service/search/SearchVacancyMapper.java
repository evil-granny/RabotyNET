package ua.softserve.ita.service.search;

import lombok.extern.slf4j.Slf4j;
import ua.softserve.ita.dto.search.SearchVacancyDto;

@Slf4j
public class SearchVacancyMapper {

    public SearchVacancyDto getSearchVacancyDto(String result) {
        String[] resultArray = result.split(",");
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = resultArray[i].replace("[", "");
            resultArray[i] = resultArray[i].replace("]", "");
            resultArray[i] = resultArray[i].replace("\"", "");
        }

        return SearchVacancyDto.builder()
                .position(resultArray[0])
                .salary(resultArray[1])
                .employment(resultArray[2])
                .vacancyId(Integer.valueOf(resultArray[3]))
                .companyId(Integer.valueOf(resultArray[4]))
                .companyName(resultArray[5])
                .city(resultArray[6])
                .build();
    }

}
