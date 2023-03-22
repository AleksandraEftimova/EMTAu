package webp.testau.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webp.testau.model.Category;
import webp.testau.model.Manufacturer;
import webp.testau.model.Product;
import webp.testau.service.CategoryService;
import webp.testau.service.ManufacturerService;
import webp.testau.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    //injecting dependancies
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public ProductController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    //vrakjame html strana
    @GetMapping
    public String getProductPage (@RequestParam(required = false) String error, Model model){
        //proverka dali treba da frlime error
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        //vrakjame produkti
        List<Product> products = this.productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    //za brisenje mora da znaeme id koe go zemame vaka
    // /products/67 -> 67 e path variable
    // /products?id=78 -> ova 78 bi se zemalo so @RequestVariable
    @DeleteMapping("/delete/{id}")
    public String deleteProduct (@PathVariable Long id){
        this.productService.deleteById(id);
        //pak prikazi ja istata strana
        return "redirect:/products";
    }

    //mapping za  strana so forma za dodavanje/editiranje proizvod
    //ni treba model oti gi listame site i po izbor da gi odbere
    @GetMapping("/add-form")
    //ogranicuva pristap do formata samo za admin role useri
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model){
        List<Category> categories = this.categoryService.listCategories();
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("bodyContent", "addProduct");
        return "master-template";
    }

    //editing product
    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model){
        //proverka dali tuka ima product so toj id, zemi go i povikaj get
        if (this.productService.findById(id).isPresent()) {
            Product product = this.productService.findById(id).get();

            List<Category> categories = this.categoryService.listCategories();
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "addProduct");
            return "master-template";
        }
        //ako ne pa prikazi greska (tuka napravivme promena vo getProductPage pogore
        return "redirect:/products?error=ProductNotFound";
    }

    //ni treba post mapping za da se izvrsi formata za zacuvuvanje
    @PostMapping("/add")
    public String saveProduct(@RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam Long category,
                              @RequestParam Long manufacturer){
        if(id!=null) {
            this.productService.edit(id, name, price, quantity, category, manufacturer);
        } else {
            //se zacuvuva proizvodot
            this.productService.save(name, price, quantity, category, manufacturer);
        }

        //vrakjame redirect kon site proizvodi
        return "redirect:/products";
    }
}
