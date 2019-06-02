package ua.softserve.ita.dto.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResumeDto {

    private long id;

    private String firstName;

    private String lastName;

    private int age;

    private String position;

    private long resumeId;

    private String city;

    private String phoneNumber;

    private String email;

}
