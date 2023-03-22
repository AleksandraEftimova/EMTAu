package webp.testau.model.dto;

import lombok.Data;

//objekt klasa so del od toa sto go cuvame za nekoja druga klasa,
//postoi oti nekogas ne sakame da predademe se, pr tuka nema id
@Data
public class ProductDto {

    private String name;
    private Double price;
    private Integer quantity;
    private Long category;
    private Long manufacturer;

    public ProductDto(){}

    public ProductDto(String name, Double price, Integer quantity, Long category, Long manufacturer) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.manufacturer = manufacturer;
    }
}
