package com.example.recipe_2.presentation;

import com.example.recipe_2.business.Recipe;
import com.example.recipe_2.business.RecipeService;
import com.example.recipe_2.security.LoginService;
import com.example.recipe_2.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.beans.Encoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipeController {

    List<Recipe> recipeList = new ArrayList<>();

    @Autowired
    RecipeService recipeService;

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder encoder;

// Project handlers ---------------------------------------------------

    @PostMapping("/api/recipe/new")
    public long handlePostNew(@RequestBody @Valid Recipe rec_2, Authentication auth) {
        Recipe toBeReturned = recipeService.saveRecipe(new Recipe(rec_2.getName(), rec_2.getDescription(), rec_2.getIngredients(), rec_2.getDirections(), rec_2.getCategory(), LocalDateTime.now(), auth.getName()));
        return toBeReturned.getId();
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe handleGetNew(@PathVariable long id) {
        return recipeService.findByRecipeId(id).get();
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@PathVariable long id, Authentication auth){
        if(recipeService.findByRecipeId(id).isPresent()) {
            if (auth.getName().equals(recipeService.findByRecipeId(id).get().getLogin())) {
                recipeService.deleteRecipe(id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/recipe/{id}")
    public void putRecipe(@PathVariable long id, @RequestBody @Valid Recipe rec_3, Authentication auth) {
        if (recipeService.findByRecipeId(id).isPresent()) {
            if(auth.getName().equals(recipeService.findByRecipeId(id).get().getLogin())) {
                Recipe newRecipe = new Recipe();
                newRecipe.setId(id);
                newRecipe.setName(rec_3.getName());
                newRecipe.setDescription(rec_3.getDescription());
                newRecipe.setDirections(rec_3.getDirections());
                newRecipe.setIngredients(rec_3.getIngredients());
                newRecipe.setCategory(rec_3.getCategory());
                newRecipe.setDate(LocalDateTime.now());
                newRecipe.setLogin(auth.getName());
                recipeService.saveRecipe(newRecipe);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> searchRecipe(@RequestParam Map<String, String> allParams) {
        List<Recipe> returnList = new ArrayList<>();
        if (allParams.size() > 1 || allParams.size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (allParams.containsKey("category") || allParams.containsKey("name")) {
            if (allParams.containsKey("category")) { // category search
                returnList = recipeService.findByCategory(allParams.get("category"));
            } else {   // name search
                returnList = recipeService.findByName(allParams.get("name"));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return returnList;
    }

    @PostMapping("/api/register")
    public void register(@RequestBody @Valid User user) {
        if (loginService.findByEmail(user.getEmailAddress()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            loginService.saveLogin(new User(user.getEmailAddress(), encoder.encode(user.getPassword())));
        }
    }
}