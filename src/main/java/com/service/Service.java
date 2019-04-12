package com.service;

import java.util.List;

public interface Service<T> {
    List<T> findAll();

    void deleteById(Long id);

    T findById(Long id);

    Long insert(T ob);

    Long update(T ob, Long id);
}
