package emt.emtau;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import emt.emtau.model.enumerations.Role;
import emt.emtau.model.User;
import emt.emtau.model.exceptions.*;
import emt.emtau.repository.jpa.UserRepository;
import emt.emtau.service.implementations.UserServiceImpl;

import java.util.Optional;

//za da go startuvame junit testot
@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {

    //zavisnosti, so mock kazuvame deka ke simulirame nivni povici
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    //ne treba da e anotiran
    private UserServiceImpl service;

    //pred site testovi prvo ke se izvrsi init metodov
    @Before
    public void init() {
        //procesiraj gi site mock anotacii i kreirani simulirani vrednosti
        MockitoAnnotations.initMocks(this);
        User user = new User("username", "password", "name", "surname", Role.ROLE_USER);
        //simulacija sto da se sluci koga pravime povik
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        //inicijalizacija na service
        this.service = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder));
    }

    //testovi za razlicni scenarija
    //test za uspecna registracija
    @Test
    public void testSuccessRegister() {

        User user = this.service.register("username", "password", "password", "name", "surname", Role.ROLE_USER);

        //proverka deka navistina e povikan toj metod
        Mockito.verify(this.service).register("username", "password", "password", "name", "surname", Role.ROLE_USER);

        //dali e uspesno registriran
        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("username does not match", "username", user.getUsername());
        Assert.assertEquals("password does not match", "password", user.getPassword());
        Assert.assertEquals("name does not match", "name", user.getName());
        Assert.assertEquals("surname does not match", "surname", user.getSurname());
        Assert.assertEquals("role does not match", Role.ROLE_USER, user.getRole());

    }

    //prv uslov
    //if (username==null || username.isEmpty() || password==null || password.isEmpty())
    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(null, "password", "password", "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).register(null, "password", "password", "name", "surename", Role.ROLE_USER);
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, "password", "password", "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, "password", "password", "name", "surename", Role.ROLE_USER);
    }

    @Test
    public void testEmptyPassword() {
        String username = "username";
        String password = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, password, "password", "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, "password", "name", "surename", Role.ROLE_USER);
    }

    @Test
    public void testNullPassword() {
        String username = "username";
        String password = null;
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, password, "password", "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, "password", "name", "surename", Role.ROLE_USER);
    }


    //vtor uslov
    //if (!password.equals(repeatPassword))
    @Test
    public void testPasswordMismatch() {
        String username = "username";
        String password = "password";
        String confirmPassword = "otherPassword";
        Assert.assertThrows("PasswordsDoNotMatchException expected",
                PasswordDoNotMatchException.class,
                () -> this.service.register(username, password, confirmPassword, "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, confirmPassword, "name", "surename", Role.ROLE_USER);
    }

    //tret uslov
    //if (this.userRepository.findByUsername(username).isPresent())
    @Test
    public void testDuplicateUsername() {
        User user = new User("username", "password", "name", "surename", Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UsernameAlreadyExistsException.class,
                () -> this.service.register(username, "password", "password", "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, "password", "password", "name", "surename", Role.ROLE_USER);
    }

}
