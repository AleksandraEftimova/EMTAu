package webp.testau.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    //roles
    ROLE_USER, ROLE_ADMIN;

    //se povikuva koga user gets a role
    @Override
    public String getAuthority() {
        //vrakja role od pogore tie
        return name();
    }
}
