package webp.testau.service.implementations;

import org.springframework.stereotype.Service;
import webp.testau.model.User;
import webp.testau.model.exceptions.InvalidArgumentsException;
import webp.testau.model.exceptions.InvalidUserCredentialsException;
import webp.testau.model.exceptions.PasswordDoNotMatchException;
import webp.testau.model.exceptions.UsernameAlreadyExistsException;
import webp.testau.repository.impl.InMemoryUserRepository;
import webp.testau.repository.jpa.UserRepository;
import webp.testau.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    //zavisnosti, podobro da se site final
//    private final InMemoryUserRepository userRepository;
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        //ako nema vneseno argumenti frlame exception
        if (username==null || username.isEmpty() || password==null ||password.isEmpty()){
            throw new InvalidArgumentsException();
        }

        //ako nema korisnik so tie username i password, frli exception
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }

    //vo UserServiceImpl prefrleno
//    @Override
//    public User register(String username, String password, String repeatPassword, String name, String surname) {
//        //proverka
//        if (username==null || username.isEmpty() || password==null ||password.isEmpty()){
//            throw new InvalidArgumentsException();
//        }
//        if (!password.equals(repeatPassword)){
//            throw new PasswordDoNotMatchException();
//        }
//
//        if (this.userRepository.findByUsername(username).isPresent()
//            || !this.userRepository.findByUsername(username).isEmpty()){
//            throw new UsernameAlreadyExistsException(username);
//        }
//        //ako ne postoi takov korisnik togas go kreirame
//        User user = new User(username, password, name, surname);
//        return userRepository.save(user);
//    }
}
