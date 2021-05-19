package guru.springframework.spring5recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeRepository recipeRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findCommandByIdAndRecipeId(Long id, Long recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (!optionalRecipe.isPresent()) {
            log.error("Recipe with ID " + recipeId + " not found.");
            return null;
        }

        var recipe = optionalRecipe.get();

        Optional<IngredientCommand> optionalCommand = recipe.getIngredients()
                                                            .stream()
                                                            .filter(
                                                                ingredient -> ingredient.getId()
                                                                                        .equals(id)
                                                            )
                                                            .map(
                                                                ingredientToIngredientCommand::convert
                                                            )
                                                            .findFirst();

        if (!optionalCommand.isPresent()) {
            log.error("IngredientCommand with ID " + id + " not found.");
            return null;
        }

        return optionalCommand.get();
    }

}
