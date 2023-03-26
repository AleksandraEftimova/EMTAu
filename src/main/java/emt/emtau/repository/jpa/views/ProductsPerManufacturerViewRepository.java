package emt.emtau.repository.jpa.views;

import emt.emtau.model.views.ProductsPerManufacturerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductsPerManufacturerViewRepository
        extends JpaRepository<ProductsPerManufacturerView, Long> {

    //ima potreba da ima refresh
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW public.products_per_manufacturer", nativeQuery = true)
    void refreshMaterializedView();
}
