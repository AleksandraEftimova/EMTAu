package emt.emtau.service;

import emt.emtau.model.User;

public interface AuthenticationService {

    User login(String username, String password);

    //vo UserService e
//    User register(String username, String password, String repeatPassword,
//                  String name, String surname);
}
