package ua.softserve.ita.dao;

import java.util.List;

public interface Dao<T> {

    T findById(Long id);

    List<T> findAll();

    Long insert(T t);

    Long update(T t, Long id);

    void deleteById(Long id);

}
