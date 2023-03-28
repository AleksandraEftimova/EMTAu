package emt.emtau.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    private LocalDateTime validUntil;

//    @OneToMany(mappedBy = "discount")
//    private List<User> user;

    @ManyToMany
    private List<User> users;

    public Discount() {}

    //new discount with new users
    public Discount(LocalDateTime validUntil) {
        this.dateCreated = dateCreated;
        this.validUntil = validUntil;
        this.users = new ArrayList<>();
    }
}
