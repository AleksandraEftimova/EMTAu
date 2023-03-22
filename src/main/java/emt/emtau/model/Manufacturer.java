package webp.testau.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//@Table(name="manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;

//    @Column(name="manufacturer-address")
    private String address;

    public Manufacturer(String name, String address) {
//        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.address = address;
    }

    public Manufacturer() {

    }
}
