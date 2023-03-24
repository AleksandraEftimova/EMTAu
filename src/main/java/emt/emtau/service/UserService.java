package emt.emtau.service;

import emt.emtau.model.User;
import emt.emtau.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;


//extends i pravi proverka dali userot postoi vo nasata baza
public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword,
                  String name, String surname, Role role);
}
