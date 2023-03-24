package emt.emtau.service.implementations;

import emt.emtau.model.Product;
import emt.emtau.model.ShoppingCart;
import emt.emtau.model.User;
import emt.emtau.model.enumerations.ShoppingCartStatus;
import emt.emtau.model.exceptions.ProductAlreadyInShoppingCartException;
import emt.emtau.model.exceptions.ProductNotFoundException;
import emt.emtau.model.exceptions.ShoppingCartNotFoundException;
import emt.emtau.model.exceptions.UserNotFoundException;
import emt.emtau.repository.jpa.ProductRepository;
import emt.emtau.repository.jpa.ShoppingCartRepository;
import emt.emtau.repository.jpa.UserRepository;
import org.springframework.stereotype.Service;
import emt.emtau.service.ShoppingCartService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    //injections
//    private final InMemoryShoppingCartRepository shoppingCartRepository;
//    private final InMemoryUserRepository userRepository;
//    private final InMemoryProductRepository productRepository;

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

//    public ShoppingCartServiceImpl(InMemoryShoppingCartRepository shoppingCartRepository, InMemoryUserRepository userRepository, InMemoryProductRepository productRepository) {
//        this.shoppingCartRepository = shoppingCartRepository;
//        this.userRepository = userRepository;
//        this.productRepository = productRepository;
//    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        //ako ne postoi kosnickata frli exception
        if(!this.shoppingCartRepository.findById(cartId).isPresent()) {
            throw new ShoppingCartNotFoundException(cartId);
        }
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        //ako nemam aktivna Created kosnicka togas kreirame nova
//        return this.shoppingCartRepository
//                .findByUsernameAndStatus(username, ShoppingCartStatus.CREATED)
//                .orElseGet( () -> {
//                    User user = this.userRepository.findByUsername(username)
//                            .orElseThrow( () -> new UserNotFoundException(username));
//                    ShoppingCart shoppingCart = new ShoppingCart(user);
//                    return this.shoppingCartRepository.save(shoppingCart);
//                });
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        //vrati ako ima shopping cart, ako ne napravi nova
        return this.shoppingCartRepository.findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet (() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
        });

    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        //pronaogjanje na produktot sto sakame da go dodademe
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        //dali produktot postoi vo kosnickata, ima poveke od 0 so isto id
        if(shoppingCart.getProducts()
                .stream().filter(r->r.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0) {
            throw new ProductAlreadyInShoppingCartException(productId, username);
        }

        //go dodavame produktot i zacuvuvame
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
