package ua.softserve.ita.service;

import ua.softserve.ita.model.profile.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<Person> findById(Long id);

    List<Person> findAll();

    Person save(Person vacancy);

    Person update(Person vacancy);

    void deleteById(Long id);

}
