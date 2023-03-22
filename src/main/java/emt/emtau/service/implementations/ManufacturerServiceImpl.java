package webp.testau.service.implementations;

import org.springframework.stereotype.Service;
import webp.testau.model.Manufacturer;
import webp.testau.repository.impl.InMemoryManufacturerRepository;
import webp.testau.repository.jpa.ManufacturerRepository;
import webp.testau.service.ManufacturerService;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    //zavisnosti
//    private final InMemoryManufacturerRepository manufacturerRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return this.manufacturerRepository.findById(id);
    }

    @Override
    public Optional<Manufacturer> save(String name, String address) {
        return Optional.of(this.manufacturerRepository.save(new Manufacturer(name, address)));
    }

    @Override
//    public boolean deleteById(Long id) {
//        return this.manufacturerRepository.deleteById(id);
//    }
    public void deleteById(Long id) {
        this.manufacturerRepository.deleteById(id);
    }
}
