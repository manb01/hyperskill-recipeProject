package com.example.recipe_2.persistence;

import com.example.recipe_2.business.Recipe;
import com.example.recipe_2.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecipeRepo extends CrudRepository<Recipe, Long> {
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findByNameIgnoreCaseOrderByDateDesc(String Name);

}
