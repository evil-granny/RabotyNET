package ua.softserve.ita.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto {

    private Long addressId;

    private String country;

    private String city;

    private String street;

    private String building;

    private String apartment;

    private Integer zipCode;

}
