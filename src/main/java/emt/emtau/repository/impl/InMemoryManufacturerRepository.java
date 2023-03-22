package webp.testau.repository.impl;

import org.springframework.stereotype.Repository;
import webp.testau.bootstrap.DataHolder;
import webp.testau.model.Manufacturer;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryManufacturerRepository {

    //listanje na site proizvoditeli
    public List<Manufacturer> findAll() {
        return DataHolder.manufacturers;
    }

    //listanje na eden proizvoditel spored id
    public Optional<Manufacturer> findById(Long id){
        return DataHolder.manufacturers
                .stream()
                .filter(r->r.getId().equals(id))
                .findFirst();
    }

    public Optional<Manufacturer> save(String name, String address) {
        Manufacturer manufacturer = new Manufacturer(name, address);
        DataHolder.manufacturers.add(manufacturer);
        return Optional.of(manufacturer);
    }

    public boolean deleteById(Long id) {
        return DataHolder.manufacturers.removeIf(i->i.getId().equals(id));
    }

}
