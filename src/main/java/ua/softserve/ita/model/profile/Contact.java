package ua.softserve.ita.model.profile;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contact")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 13)
    private String phoneNumber;

}
