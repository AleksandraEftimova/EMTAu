package emt.emtau.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import emt.emtau.model.ShoppingCart;
import emt.emtau.model.User;
import emt.emtau.model.enumerations.ShoppingCartStatus;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
}
