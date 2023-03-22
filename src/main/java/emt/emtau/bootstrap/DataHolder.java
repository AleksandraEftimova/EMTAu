package webp.testau.bootstrap;

import org.springframework.stereotype.Component;
import webp.testau.model.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

//ja pravime komponent za da moze pri app start da bide instancirana
@Component
public class DataHolder {
    //lista od kategorii sto ke gi cuvame
    public static List<Category> categories = new ArrayList<>();

    //lista od korisnici
    public static List<User> users = new ArrayList<>();

    //lista od proizvoditeli
    public static List<Manufacturer> manufacturers = new ArrayList<>();

    //lsita od produkti
    public static List<Product> products = new ArrayList<>();

    //lista od kosnicki
    public static List<ShoppingCart> shoppingCarts = new ArrayList<>();

//    @PostConstruct
//    public void init(){
//        categories.add(new Category("Books", "Books category"));
//        categories.add(new Category("Movies", "Movies category"));
//        categories.add(new Category("Software", "Software category"));
//
//        users.add(new User("Username1", "pass1", "Name1", "Surname1"));
//        users.add(new User("Username2", "pass2", "Name2", "Surname2"));
//
//        Manufacturer manufacturer = new Manufacturer("Nike", "NYNY");
//        manufacturers.add(manufacturer);
//
//        Category category = new Category("Sport", "Sport category");
//        categories.add(category);
//        products.add(new Product("Ball1", 127.0, 7, category, manufacturer));
//        products.add(new Product("Ball2", 127.0, 7, category, manufacturer));
//        products.add(new Product("Ball3", 127.0, 7, category, manufacturer));
//
//    }
}
