package webp.testau.service;


import webp.testau.model.Product;
import webp.testau.model.dto.ProductDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    //za da ne go koristime celiot objekt kako parametar, odime po identificator birame od lista
    Optional<Product> save(String name, Double price, Integer quantity,
                           Long categoryId, Long manufacturerId);



    Optional<Product> edit(Long id, String name, Double price, Integer quantity, Long categoryId, Long manufacturerId);

    void deleteById(Long id);

    Optional<Product> save(ProductDto productDto);

    Optional<Product> edit(Long id, ProductDto productDto);
}
