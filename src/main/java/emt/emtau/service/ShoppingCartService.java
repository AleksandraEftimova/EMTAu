package webp.testau.service;

import webp.testau.model.Product;
import webp.testau.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    //vrakja site produkti vo edna kosnicka
    List<Product> listAllProductsInShoppingCart(Long cartId);

    //aktivna
    ShoppingCart getActiveShoppingCart (String username);

    //dodavanje produkti vo kosnicka
    ShoppingCart addProductToShoppingCart (String username, Long productId);
}
