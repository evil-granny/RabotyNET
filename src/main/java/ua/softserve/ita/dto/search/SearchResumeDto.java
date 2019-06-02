package ua.softserve.ita.dto.search;

import lombok.*;

@Data
@Builder
public class SearchResumeDto {

    private BigInteger id;

    private String firstName;

    private String lastName;

    private int age;

    private String position;

    private BigInteger resumeId;

    private String city;

    private String phoneNumber;

    private String email;

}
