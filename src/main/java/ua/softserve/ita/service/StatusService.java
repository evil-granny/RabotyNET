package ua.softserve.ita.service;

import ua.softserve.ita.model.Status;

import java.util.List;
import java.util.Optional;

public interface StatusService {
    Optional<Status> findById(Long id);

    List<Status> findAll();

    Status save(Status status);

    Status update(Status status);

    void deleteById(Long id);
}
