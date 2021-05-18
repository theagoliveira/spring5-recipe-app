package guru.springframework.spring5recipeapp.services;

import java.util.Set;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;

public interface RecipeService {

    Recipe findById(Long id);

    Set<Recipe> findAll();

    RecipeCommand saveCommand(RecipeCommand command);

}
