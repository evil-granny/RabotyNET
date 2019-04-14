package ua.softserve.ita.service;

import java.util.List;

public interface Service<T> {

    T findById(Long id);

    List<T> findAll();

    Long insert(T t);

    Long update(T t, Long id);

    void deleteById(Long id);

}
