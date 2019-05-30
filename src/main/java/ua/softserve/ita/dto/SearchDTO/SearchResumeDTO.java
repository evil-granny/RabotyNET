package ua.softserve.ita.dto.SearchDTO;

import lombok.*;

import java.math.BigInteger;

@Data
@Builder
public class SearchResumeDTO {

    private BigInteger id;

    private String firstName;

    private String lastName;

    private int age;

    private String position;

    private BigInteger resumeId;

    private String city;

    private String phoneNumber;
}
