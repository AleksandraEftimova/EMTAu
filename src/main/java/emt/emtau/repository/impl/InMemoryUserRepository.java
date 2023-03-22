package webp.testau.repository.impl;

import org.springframework.stereotype.Repository;
import webp.testau.bootstrap.DataHolder;
import webp.testau.model.User;

import java.util.Optional;

@Repository
public class InMemoryUserRepository {

    //najdi korisnik spored username
    public Optional<User> findByUsername(String username){
        return DataHolder.users
                .stream()
                .filter(r->r.getUsername().equals(username))
                .findFirst();
    }

    //najdi korisnik spored username i password
    public Optional<User> findByUsernameAndPassword(String username, String password){
        return DataHolder.users.stream().filter(r->r.getUsername().equals(username) &&
                r.getPassword().equals(password)).findFirst();
    }

    public User saveOrUpdate(User user){
        DataHolder.users.removeIf(r->r.getUsername().equals(user.getUsername()));
        DataHolder.users.add(user);
        return user;
    }

    public void delete(String username){
        DataHolder.users.removeIf(r->r.getUsername().equals(username));
    }
}
