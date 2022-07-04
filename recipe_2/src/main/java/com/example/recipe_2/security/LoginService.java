package com.example.recipe_2.security;

import com.example.recipe_2.persistence.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private final LoginRepo loginService;

    LoginService(LoginRepo userService) {
        this.loginService = userService;
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(loginService.findByEmailAddress(email));
    }
    public User saveLogin(User u) {
        return loginService.save(u);
    }
}
