package webp.testau.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import webp.testau.model.User;
import webp.testau.model.enumerations.Role;

//extends i pravi proverka dali userot postoi vo nasata baza
public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword,
                  String name, String surname, Role role);
}
