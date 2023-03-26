package emt.emtau.model.views;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Subselect("SELECT * FROM public.products_per_manufacturer")
@Immutable
public class ProductsPerManufacturerView {

    @Id
    @Column(name = "manufacturer_id")
    private Long manufacturerId;

    @Column(name = "num_products")
    private Integer numProducts;
}
