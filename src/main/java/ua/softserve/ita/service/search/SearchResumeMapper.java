package ua.softserve.ita.service.search;

import lombok.extern.slf4j.Slf4j;
import ua.softserve.ita.dto.search.SearchResumeDto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Slf4j
public class SearchResumeMapper {

    public SearchResumeDto getSearchResumeDto(String result) {
        String[] resultArray = result.split(",");
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = resultArray[i].replace("[", "");
            resultArray[i] = resultArray[i].replace("]", "");
            resultArray[i] = resultArray[i].replace("\"", "");
        }

        return SearchResumeDto.builder()
                .id(Long.valueOf(resultArray[0]))
                .firstName(resultArray[1].trim())
                .lastName(resultArray[2].trim())
                .age(Period.between(Instant.ofEpochMilli(Long.valueOf(resultArray[3])).atZone(ZoneId.systemDefault())
                        .toLocalDate(), LocalDate.now()).getYears())
                .position(resultArray[4].trim())
                .resumeId(Long.valueOf(resultArray[5].trim()))
                .phoneNumber(resultArray[6].trim())
                .email(resultArray[7].trim())
                .city(resultArray[8].trim())
                .build();
    }

}
