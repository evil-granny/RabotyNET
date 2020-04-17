package ua.com.service;

import ua.com.model.profile.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Optional<Address> findById(Long id);

    List<Address> findAll();

    Address save(Address address);

    Address update(Address address);

    void deleteById(Long id);

}
