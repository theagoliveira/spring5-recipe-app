package guru.springframework.spring5recipeapp.services;

import java.util.Set;

import guru.springframework.spring5recipeapp.domain.Recipe;

public interface RecipeService {

    Set<Recipe> findAll();

}
