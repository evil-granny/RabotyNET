package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ua.softserve.ita.adapter.LocalDateAdapter;
import ua.softserve.ita.adapter.LocalDateDeserializer;
import ua.softserve.ita.adapter.LocalDateSerializer;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "photo")
    private byte[] photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contacts_id", referencedColumnName = "contacts_id", nullable = false)
    private Contacts contacts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private Address address;

    /* @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "person")
    private List<CV> cvs; */

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /* public List<CV> getCvs() {
        return cvs;
    }

    public void setCvs(List<CV> cvs) {
        this.cvs = cvs;
    } */

    /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return userId == person.userId &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                birthday.equals(person.birthday) &&
                contacts.equals(person.contacts) &&
                address.equals(person.address) &&
                cvs.equals(person.cvs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, birthday, contacts, address, cvs);
    } */

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", contacts='" + contacts + '\'' +
                ", address=" + address +
                '}';
    }

}
