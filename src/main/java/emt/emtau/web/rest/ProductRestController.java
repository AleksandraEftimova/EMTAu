package emt.emtau.web.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import emt.emtau.model.Product;
import emt.emtau.model.dto.ProductDto;
import emt.emtau.service.ProductService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    //ne vrakjame templates tuku lista podatoci
    @GetMapping
    private List<Product> findAll(){
        return this.productService.findAll();
    }

    //vrakjame podatoci
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return this.productService.findById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Product> save(@RequestBody ProductDto productDto) {
        return this.productService.save(productDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Product> save(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return this.productService.edit(id, productDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.productService.deleteById(id);
        if (this.productService.findById(id).isEmpty())
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
