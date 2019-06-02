package ua.softserve.ita.model.profile;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contact")
@Data
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

}
