package ua.softserve.ita.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long addressId;

    @Column(name = "country",nullable = false,length = 30)
    @NotNull(message = "country must be not null")
    @NotBlank(message = "country must be not blank")
    @Pattern(regexp = "^[A-Za-z+? ?-A-Za-z]{3,30}$", message = "country name is incorrect")
    private String country;

    @Column(name = "city",nullable = false,length = 20)
    @NotNull(message = "city must be not null")
    @NotBlank(message = "city must be not blank")
    @Pattern(regexp = "^[A-Za-z+? ?-A-Za-z]{3,20}$", message = "city name is incorrect")
    private String city;

    @Column(name = "street",length = 30)
    @Size(max = 30, message = "street name is too long")
    private String street;

    @Column(name = "building",length = 5)
    @Size(max = 5, message = "building name is too long")
    private String building;

    @Column(name = "apartment",length = 5)
    @Size(max = 5, message = "apartment name is too long")
    private String apartment;

    @Column(name = "zip_code", length = 5)
    @Min(value = 10000, message = "zip code is incorrect")
    @Max(value = 99999, message = "zip code is incorrect")
    private Integer zipCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId &&
                country.equals(address.country) &&
                city.equals(address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(building, address.building) &&
                Objects.equals(apartment, address.apartment) &&
                Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, country, city, street, building, apartment, zipCode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", apartment='" + apartment + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }

}
