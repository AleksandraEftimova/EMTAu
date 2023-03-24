package emt.emtau.repository.impl;

import org.springframework.stereotype.Repository;
import emt.emtau.bootstrap.DataHolder;
import emt.emtau.model.Category;
import emt.emtau.model.Manufacturer;
import emt.emtau.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository {

    //najdi gi site
    public List<Product> findAll() {
        return DataHolder.products;
    }

    //najdi spored id
    public Optional<Product> findById(Long id){
        return DataHolder.products
                .stream()
                .filter(r->r.getId().equals(id))
                .findFirst();
    }

    //najdi spored id
    public Optional<Product> findByName(String name){
        return DataHolder.products
                .stream()
                .filter(r->r.getName().equals(name))
                .findFirst();
    }

    //CRUD funkcionalnost
    //save
    public Optional<Product> save(String name, Double price, Integer quantity,
                                  Category category, Manufacturer manufacturer) {

        //otstrani produkt sto go ima idto ili imeto
        DataHolder.products.removeIf(r->r.getName().equals(name));
        //kreirame proizvod
        Product product = new Product(name, price, quantity, category, manufacturer);
        DataHolder.products.add(product);
        return Optional.of(product);
    }
    //delete by id
    public void deleteById(Long id){
        DataHolder.products.removeIf(r->r.getId().equals(id));
    }
}
