package webp.testau.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//preku metodi kazuvame koi ulogi do sto imaat pristap
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //injecting Password Encoder
    private final PasswordEncoder passwordEncoder;
    private final CustomUsernamePasswordAuthenticationProvider customUsernamePasswordAuthenticationProvider;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, CustomUsernamePasswordAuthenticationProvider customUsernamePasswordAuthenticationProvider) {
        this.passwordEncoder = passwordEncoder;
        this.customUsernamePasswordAuthenticationProvider = customUsernamePasswordAuthenticationProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Disabling Cross-Site Request Forgery napad
        // (attack which forces authenticated users to submit a request to a Web app
        // against which they are currently authenticated)???
        http.csrf().disable()
                .authorizeRequests()
                //koi strani treba da se dostapni/dozvoleni od koi korisnici
                //dozvoleni za site
                .antMatchers("/", "/home", "/assets/**", "/register", "/products").permitAll()
                //samo za admini
                .antMatchers("/admin/**").hasRole("ADMIN")
                //site ostanati sto ne se spomenati
                .anyRequest().authenticated()
                //isto taka na site dozvoli pristap do login stranata
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                //ako e neuspesna najavata treba da kazeme kade ke e directiran
                    .failureUrl("/login?error=BadCredentials")
                //ako e uspesna pak redirect na products
                    .defaultSuccessUrl("/products", true)
                //logout
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                //za ubava strana pri exception za denied forbidden
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");

    }

    //managed by the authentication provider
    //vo ovoj slucaj InMemoryAuthetication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("Username1")
//                //enkripcija na passwordite
//                .password(passwordEncoder.encode("pass1"))
//                //authorities, permisii
//                .authorities("ROLE_USER")
//                //dodavanje sleden korisnik
//                .and()
//                .withUser("Admin")
//                .password(passwordEncoder.encode("admin"))
//                .authorities("ROLE_ADMIN");
        auth.authenticationProvider(customUsernamePasswordAuthenticationProvider);
    }
}
