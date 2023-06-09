package emt.emtau.service.implementations;

import emt.emtau.model.Category;
import emt.emtau.model.Manufacturer;
import emt.emtau.model.Product;
import emt.emtau.model.dto.ProductDto;
import emt.emtau.model.event.ProductCreatedEvent;
import emt.emtau.model.exceptions.CategoryNotFoundException;
import emt.emtau.model.exceptions.ManufacturerNotFoundException;
import emt.emtau.model.exceptions.ProductNotFoundException;
import emt.emtau.repository.jpa.CategoryRepository;
import emt.emtau.repository.jpa.ManufacturerRepository;
import emt.emtau.repository.jpa.ProductRepository;
import emt.emtau.repository.jpa.views.ProductsPerManufacturerViewRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import emt.emtau.service.ProductService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    //zavisnosti, inject the repository
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductsPerManufacturerViewRepository productsPerManufacturerViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository, ProductsPerManufacturerViewRepository productsPerManufacturerViewRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.productsPerManufacturerViewRepository = productsPerManufacturerViewRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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
        Product product = this.productRepository.save(new Product(name, price, quantity, category, manufacturer));
        //refresh na view pri promena
        //this.refreshMaterializedView();
        //publish an event (refresh)
        this.applicationEventPublisher.publishEvent(new ProductCreatedEvent(product));
        return Optional.of(product);
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

        this.productRepository.save(product);
        //this.refreshMaterializedView();

        return Optional.of(product);

    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> save(ProductDto productDto) {
        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        Manufacturer manufacturer = this.manufacturerRepository.findById(productDto.getManufacturer())
                .orElseThrow(() -> new ManufacturerNotFoundException(productDto.getManufacturer()));

        this.productRepository.deleteByName(productDto.getName());
        Product product = this.productRepository.save(new Product(productDto.getName(), productDto.getPrice(), productDto.getQuantity(), category, manufacturer));
        //refresh na view pri promena
        //this.refreshMaterializedView();
        //publish an event (refresh)
        this.applicationEventPublisher.publishEvent(new ProductCreatedEvent(product));
        return Optional.of(product);
    }

    @Override
    public Optional<Product> edit(Long id, ProductDto productDto) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        product.setCategory(category);

        Manufacturer manufacturer = this.manufacturerRepository.findById(productDto.getManufacturer())
                .orElseThrow(() -> new ManufacturerNotFoundException(productDto.getManufacturer()));
        product.setManufacturer(manufacturer);

        this.productRepository.save(product);
        //this.refreshMaterializedView();
        return Optional.of(product);
    }

    @Override
    public void refreshMaterializedView() {
        this.productsPerManufacturerViewRepository.refreshMaterializedView();
    }
}
