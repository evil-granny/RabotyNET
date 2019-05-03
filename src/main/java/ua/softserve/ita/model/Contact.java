package ua.softserve.ita.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "contacts")
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contacts_id")
    private Long contactsId;

    @Column(name = "email", length = 50)
    @Email(message = "email is incorrect")
    @Size(max = 50, message = "email is too long")
    private String email;

    @Column(name = "phone_number", length = 20)
    @Pattern(regexp = "^[+]*[0-9][0-9][(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "phone number is incorrect")
    @Size(max = 20, message = "phone number is too long")
    private String phoneNumber;

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
        Contact contact = (Contact) o;
        return Objects.equals(contactsId, contact.contactsId) &&
                email.equals(contact.email) &&
                Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactsId, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactsId=" + contactsId +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
