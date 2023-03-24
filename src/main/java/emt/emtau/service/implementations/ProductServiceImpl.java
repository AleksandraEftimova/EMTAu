package emt.emtau.service.implementations;

import emt.emtau.model.Category;
import emt.emtau.model.Manufacturer;
import emt.emtau.model.Product;
import emt.emtau.model.dto.ProductDto;
import emt.emtau.model.exceptions.CategoryNotFoundException;
import emt.emtau.model.exceptions.ManufacturerNotFoundException;
import emt.emtau.model.exceptions.ProductNotFoundException;
import emt.emtau.repository.jpa.CategoryRepository;
import emt.emtau.repository.jpa.ManufacturerRepository;
import emt.emtau.repository.jpa.ProductRepository;
import org.springframework.stereotype.Service;

import emt.emtau.service.ProductService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    //zavisnosti, inject the repository
//    private final InMemoryProductRepository productRepository;
//    private final InMemoryCategoryRepository categoryRepository;
//    private final InMemoryManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

//    public ProductServiceImpl(InMemoryProductRepository productRepository, InMemoryCategoryRepository categoryRepository, InMemoryManufacturerRepository manufacturerRepository) {
//        this.productRepository = productRepository;
//        this.categoryRepository = categoryRepository;
//        this.manufacturerRepository = manufacturerRepository;
//    }
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Product> save(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        //vidi koja kategorija go ima toa id i koj proizvoditel toa id,
        //togas vrati metod save

        //pravime exception vo slucaj da ne ja najde kategorijata/proizvoditelot
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));

        //ako ima so isto ime izbrisi
        this.productRepository.deleteByName(name);
        //ako se pomine kako sto treba togas samo gi dodavame
        return Optional.of(this.productRepository.save(new Product(name, price, quantity, category, manufacturer)));
    }

    @Override
    @Transactional
    public Optional<Product> edit(Long id, String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.setCategory(category);

        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        product.setManufacturer(manufacturer);

        return Optional.of(this.productRepository.save(product));

    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> save(ProductDto productDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> edit(Long id, ProductDto productDto) {
        return Optional.empty();
    }
}
