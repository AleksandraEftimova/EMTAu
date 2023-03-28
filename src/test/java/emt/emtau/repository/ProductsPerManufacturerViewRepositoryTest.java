package emt.emtau.repository;

import emt.emtau.model.Product;
import emt.emtau.model.views.ProductsPerCategoryView;
import emt.emtau.repository.jpa.views.ProductsPerCategoryViewRepository;
import emt.emtau.service.CategoryService;
import emt.emtau.service.ManufacturerService;
import emt.emtau.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductsPerManufacturerViewRepositoryTest {

    @Autowired
    private ProductsPerCategoryViewRepository productsPerCategoryViewRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ProductService productService;

    @Test
    public void testProductsPerCategoryViewRepositoryFindAll(){
        List<ProductsPerCategoryView> list1 =
                this.productsPerCategoryViewRepository.findAll();
//        Assert.assertEquals(3, list.size());

        Product product = new Product();
        product.setName("Ski Jacket 557");
        product.setManufacturer(this.manufacturerService.findAll().get(0));
        product.setCategory(this.categoryService.listCategories().get(0));
        this.productService.save(product.getName(), product.getPrice(),
                product.getQuantity(), product.getCategory().getId(), product.getManufacturer().getId());

        List<ProductsPerCategoryView> list2 =
                this.productsPerCategoryViewRepository.findAll();
    }
}
