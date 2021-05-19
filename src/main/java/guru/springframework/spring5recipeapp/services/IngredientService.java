package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findCommandByIdAndRecipeId(Long id, Long recipeId);

}
