package ua.softserve.ita.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "contacts")
public class Contacts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contacts_id")
    private Long contactsId;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    /* @OneToOne(mappedBy = "contacts")
    private Person person; */

    @OneToOne(mappedBy = "contacts")
    private Company company;

    public Long getContactsId() {
        return contactsId;
    }

    public void setContactsId(Long contactsId) {
        this.contactsId = contactsId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contacts contacts = (Contacts) o;
        return Objects.equals(contactsId, contacts.contactsId) &&
                email.equals(contacts.email) &&
                Objects.equals(phoneNumber, contacts.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactsId, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "contactsId=" + contactsId +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
