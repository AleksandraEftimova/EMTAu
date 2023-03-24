package emt.emtau.service.implementations;

import org.springframework.stereotype.Service;
import emt.emtau.model.User;
import emt.emtau.model.exceptions.InvalidArgumentsException;
import emt.emtau.model.exceptions.InvalidUserCredentialsException;
import emt.emtau.model.exceptions.PasswordDoNotMatchException;
import emt.emtau.model.exceptions.UsernameAlreadyExistsException;
import emt.emtau.repository.impl.InMemoryUserRepository;
import emt.emtau.repository.jpa.UserRepository;
import emt.emtau.service.AuthenticationService;

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
