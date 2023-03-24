package emt.emtau.service.implementations;

import emt.emtau.model.User;
import emt.emtau.model.enumerations.Role;
import emt.emtau.model.exceptions.InvalidUsernameOrPasswordException;
import emt.emtau.model.exceptions.PasswordDoNotMatchException;
import emt.emtau.model.exceptions.UsernameAlreadyExistsException;
import emt.emtau.repository.jpa.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import emt.emtau.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    //zavisnosti
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        //proverka
        if (username==null || username.isEmpty() || password==null || password.isEmpty()){
//            throw new InvalidArgumentsException();
            throw new InvalidUsernameOrPasswordException();
        }
        if (!password.equals(repeatPassword)){
            throw new PasswordDoNotMatchException();
        }

        if (this.userRepository.findByUsername(username).isPresent()){
            throw new UsernameAlreadyExistsException(username);
        }
        //ako ne postoi takov korisnik togas go kreirame
        User user = new User(username, passwordEncoder.encode(password), name, surname, role);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
