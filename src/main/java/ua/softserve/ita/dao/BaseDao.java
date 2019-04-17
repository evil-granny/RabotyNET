package ua.softserve.ita.dao;

import java.util.List;

public interface BaseDao<T> {
    T findById(Long id);

    List<T> findAll();

    T insert(T t);

    T update(T t, Long id);

    void deleteById(Long id);
}
