package ua.softserve.ita.dto.SearchDTO;

import lombok.*;

@Data
@Builder
public class SearchResumeDTO {

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
