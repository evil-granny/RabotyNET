package ua.softserve.ita.service.search;

import lombok.extern.slf4j.Slf4j;
import ua.softserve.ita.dto.SearchDTO.SearchResumeDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Slf4j
public class SearchResumeMapper {

    public SearchResumeDTO getSearchResumeDTO(String result) {
        String[] resultArray = result.split(",");
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = resultArray[i].replace("[", "");
            resultArray[i] = resultArray[i].replace("]", "");
            resultArray[i] = resultArray[i].replace("\"", "");
        }

        return SearchResumeDTO.builder()
                .id(Long.valueOf(resultArray[0]))
                .firstName(resultArray[1].trim())
                .lastName(resultArray[2].trim())
                .age(Period.between(Instant.ofEpochMilli(Long.valueOf(resultArray[3])).atZone(ZoneId.systemDefault())
                        .toLocalDate(), LocalDate.now()).getYears())
                .position(resultArray[4].trim())
                .phoneNumber(resultArray[5].trim())
                .email(resultArray[6].trim())
                .city(resultArray[7].trim())
                .build();
    }
}
