package com.example.recipe_2.business;

import com.example.recipe_2.persistence.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private final RecipeRepo repository;


    public RecipeService(RecipeRepo repository) {
        this.repository = repository;
    }

    public Optional<Recipe> findByRecipeId(long id) {

        return repository.findById(id);
    }

    public Recipe saveRecipe(Recipe R) {
        return repository.save(R);
    }

    public void deleteRecipe(long id) {
        repository.deleteById(id);
    }

    public List<Recipe> findAll() {
        return (List<Recipe>) repository.findAll();
    }

    public List<Recipe> findByCategory(String category) {
        return repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return repository.findByNameIgnoreCaseOrderByDateDesc(name);
    }
}
