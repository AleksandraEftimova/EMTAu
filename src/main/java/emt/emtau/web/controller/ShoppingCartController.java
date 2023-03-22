package webp.testau.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webp.testau.model.ShoppingCart;
import webp.testau.model.User;
import webp.testau.service.ShoppingCartService;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getProductPage (@RequestParam(required = false) String error, Model model,
                                  HttpServletRequest req){
        //proverka dali treba da frlime error
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        //ne moze veke vaka
//        User user = (User) req.getSession().getAttribute("user");
        String username = req.getRemoteUser();
//        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(user.getUsername());
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCart(username);
        model.addAttribute("products", this.shoppingCartService.listAllProductsInShoppingCart(shoppingCart.getId()));
        model.addAttribute("bodyContent", "shopping-cart");
//        return "shopping-cart";
        return "master-template";
    }

    @PostMapping("/add-product/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, HttpServletRequest req, Authentication authentication) {
        try {
//            User user = (User) req.getSession().getAttribute("user");
            //promeni
//            String username = req.getRemoteUser();
//            ShoppingCart shoppingCart = this.shoppingCartService.addProductToShoppingCart(username, id);
            User user = (User) authentication.getPrincipal();
            this.shoppingCartService.addProductToShoppingCart(user.getUsername(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException exception){
            return "redirect:/shopping-cart?error=" + exception.getMessage();
        }
    }
}

