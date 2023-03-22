package webp.testau.service;

import webp.testau.model.User;

public interface AuthenticationService {

    User login(String username, String password);

    //vo UserService e
//    User register(String username, String password, String repeatPassword,
//                  String name, String surname);
}
