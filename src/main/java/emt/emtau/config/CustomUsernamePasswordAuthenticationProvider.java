package webp.testau.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import webp.testau.service.UserService;

@Component
public class CustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomUsernamePasswordAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    //se povikuva koga se vnesuvaat avtentikaciski credentials
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        //ako se prazni
        if("".equals(username) || "".equals(password)){
            throw new BadCredentialsException("Invalid Credentials");
        }

        //dali korisnikot postoi vo baza, odime so intefejs za samo dadeni podatoci
        UserDetails userDetails = this.userService.loadUserByUsername(username);
        //izvlecen e korisnikot

        //proverka dali lozinkite se matching decrypted input with pass from our base
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password is incorrect!");
        }

        //ako se e ok togas treba da vratime new instance of User i site podatoci bitni za nego
        return new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    //dali e poddrzuvan ovoj tip na najava
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
