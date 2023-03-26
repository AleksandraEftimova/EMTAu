package emt.emtau.repository.jpa;

import emt.emtau.model.enumerations.Role;
import emt.emtau.model.projections.UserProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import emt.emtau.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //metod so koj se povlekuva se sto imame za cart
    //type fetch vika deka ako site specificirani atributi ke se
    // tretirani so eager, a drugite so lazy
    //type load obratno
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"carts"})
    @Query("select u from User u")
    List<User> fetchAll();

    //metod so koj se povikuva se sto ima za carts i discount so Eager
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"carts", "discount"})
    @Query("select u from User u")
    List<User> loadAll();

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    UserProjection findByRole(Role role);

    //preku interfejs birame podatoci po custom
    @Query("select u.username, u.name, u.surname from User u")
    List<UserProjection> takeUsernameAndNameAndSurnameByProjection();


}
