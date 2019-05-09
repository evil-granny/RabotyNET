package ua.softserve.ita.dto.SearchDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SearchCVDTO {

    private long id;

    private String firstName;

    private String lastName;

    private int age;

    private String position;

    private String city;

    private String phoneNumber;

    private String email;
}
