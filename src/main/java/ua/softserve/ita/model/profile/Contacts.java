package ua.softserve.ita.model.profile;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contacts")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Contacts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contacts_id")
    private Long contactsId;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

}
