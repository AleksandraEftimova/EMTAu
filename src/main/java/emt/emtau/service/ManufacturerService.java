package webp.testau.service;

import webp.testau.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    List<Manufacturer> findAll();

    Optional<Manufacturer> findById(Long id);

    Optional<Manufacturer> save(String name, String address);

//    boolean deleteById(Long id);
    void deleteById(Long id);

}
