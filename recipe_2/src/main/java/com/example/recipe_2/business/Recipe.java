package com.example.recipe_2.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "ingredients")
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients = new ArrayList<>();

    @Column(name = "directions")
    @Size(min = 1)
    @ElementCollection
    private List<String> directions = new ArrayList<>();

    @Column(name = "category")
    @NotBlank
    private String category;

    @Column(name = "date")
    private LocalDateTime date;

    @JsonIgnore
    private String login;

    @Override
    public String toString(){
        return "{\"name\":\"" + this.name + "\"," + "\"description\":\""+this.description + "\"," + "\"ingredients\":\"" + this.ingredients + "\"," + "\"directions\":\"" + this.directions + "\"}";
    }

    public Recipe(String name, String description, List<String> ingredients, List<String> directions, String category, LocalDateTime date, String authName) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category = category;
        this.date = date;
        this.login = authName;
    }
}