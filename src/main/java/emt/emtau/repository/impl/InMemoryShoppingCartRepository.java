package emt.emtau.repository.impl;

import org.springframework.stereotype.Repository;
import emt.emtau.bootstrap.DataHolder;
import emt.emtau.model.ShoppingCart;
import emt.emtau.model.enumerations.ShoppingCartStatus;

import java.util.Optional;

@Repository
public class InMemoryShoppingCartRepository {
    //metod koj ja vrakja kosnickata so dadeno id
    public Optional<ShoppingCart> findById(Long id){
        return DataHolder.shoppingCarts
                .stream()
                .filter(i->i.getId().equals(id))
                .findFirst();
    }

    //prebaruvanje spored username i status
    public Optional<ShoppingCart> findByUsernameAndStatus(String username, ShoppingCartStatus status) {
        return DataHolder.shoppingCarts
                .stream()
                .filter(r->r.getUser().getUsername().equals(username)
                && r.getStatus().equals(status))
                .findFirst();
    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
        DataHolder.shoppingCarts
                .removeIf(i->i.getUser().getUsername().equals(shoppingCart.getUser().getUsername()));
        DataHolder.shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }

}
