package ua.softserve.ita.service;

import java.util.List;

public interface Service<T> {

    T findById(Long id);

    List<T> findAll();

    T create(T t);

    T update(T t, Long id);

    void deleteById(Long id);

}
