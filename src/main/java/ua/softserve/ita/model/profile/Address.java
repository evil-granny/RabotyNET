package ua.softserve.ita.model.profile;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Data
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "country", nullable = false, length = 30)
    private String country;

    @Column(name = "city", nullable = false, length = 30)
    private String city;

    @Column(name = "street", length = 30)
    private String street;

    @Column(name = "building", length = 5)
    private String building;

    @Column(name = "apartment", length = 5)
    private String apartment;

    @Column(name = "zip_code", length = 5)
    private Integer zipCode;

}
