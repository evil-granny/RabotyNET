package ua.com.service;

import ua.com.model.profile.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Optional<Contact> findById(Long id);

    List<Contact> findAll();

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

}
