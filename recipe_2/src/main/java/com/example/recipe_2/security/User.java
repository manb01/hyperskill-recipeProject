package com.example.recipe_2.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "login")
public class User {
    @Id
    @Email
    private String emailAddress;
    
    @Size(min = 8)
    @NotBlank
    private String password;
}

@Data
class impUserDetails implements UserDetails {
    private final String username;
    private final String password;

    private final List<GrantedAuthority> rolesAndAuth;
    public impUserDetails(User u) {
        this.username = u.getEmailAddress();
        this.password = u.getPassword();
        this.rolesAndAuth =new ArrayList<GrantedAuthority>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

@Service
class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    LoginService userRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRep.findByEmail(username).get();
        return new impUserDetails(u);
    }
}
