package com.example.recipe_2.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends CrudRepository<User, String> {
    User findByEmailAddress(String emailAddress);
}
