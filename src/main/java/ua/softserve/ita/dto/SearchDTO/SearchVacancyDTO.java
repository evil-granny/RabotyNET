package ua.softserve.ita.dto.SearchDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
@Builder
public class SearchVacancyDTO {

    private BigInteger vacancyId;

    private BigInteger companyId;

    private String position;

    private String companyName;

    private String city;

    private String employment;

    private int salary;

}
