package emt.emtau.repository;

import emt.emtau.model.views.ProductsPerCategoryView;
import emt.emtau.repository.jpa.views.ProductsPerCategoryViewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductsViewsRepositoryTest {

    @Autowired
    private ProductsPerCategoryViewRepository productsPerCategoryViewRepository;

    @Test
    public void testProductsPerCategoryViewRepositoryFindAll(){
        List<ProductsPerCategoryView> list =
            this.productsPerCategoryViewRepository.findAll();
        Assert.assertEquals(3, list.size());
    }
}
