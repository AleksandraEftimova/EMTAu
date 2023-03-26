package emt.emtau.repository.jpa.views;

import emt.emtau.model.views.ProductsPerCategoryView;
import emt.emtau.model.views.ProductsPerCategoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsPerCategoryViewRepository
        extends JpaRepository<ProductsPerCategoryView, Long> {

}
