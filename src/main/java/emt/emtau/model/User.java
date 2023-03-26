package emt.emtau.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import emt.emtau.model.enumerations.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "shop_users")
public class User implements UserDetails {
    @Id
    private String username;
    private String password;
    private String name;
    private String surname;

    //optimistic lock
    @Version
    private Long version;

    //default values for security methods
//    @Column(nullable = false)
    private boolean isAccountNonExpired;
//    @Column(nullable = false)
    private boolean isAccountNonLocked = true;
//    @Column(nullable = false)
    private boolean isCredentialNonExpired = true;
//    @Column(nullable = false)
    private boolean isEnabled = true;

    //To-Many=Lazy, To-One=Eager by default
    //Eager ako e za ManyToOne ke ima za sekoe n uste n queries
    @OneToMany(mappedBy = "user"/*, fetch = FetchType.EAGER*/)
    private List<ShoppingCart> carts;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToOne
    private Discount discount;

    public User(String username, String password, String name, String surname, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public User() {

    }

    //NAJBITEN, so nego doznava koi ulogi gi ima korisnikot
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //ako e NxN vrskata sepak 1 User can only have 1 Role
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
