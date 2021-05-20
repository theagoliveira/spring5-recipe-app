package guru.springframework.spring5recipeapp.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeRepository recipeRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

    @Override
    @Transactional
    public IngredientCommand saveCommand(IngredientCommand command) {
        Long id = command.getId();
        Long recipeId = command.getRecipeId();
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (!optionalRecipe.isPresent()) {
            log.error("Recipe with ID " + recipeId + " not found.");
            return null;
        } else {
            var recipe = optionalRecipe.get();
            Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                                                            .stream()
                                                            .filter(
                                                                ingredient -> ingredient.getId()
                                                                                        .equals(id)
                                                            )
                                                            .findFirst();

            if (!optionalIngredient.isPresent()) {
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            } else {
                var ingredient = optionalIngredient.get();
                ingredient.setDescription(command.getDescription());
                ingredient.setAmount(command.getAmount());
                ingredient.setUom(
                    unitOfMeasureRepository.findById(
                        command.getUom().getId()
                    ).orElseThrow(() -> new RuntimeException("Unit of Measure not found."))
                );
            }

            var savedRecipe = recipeRepository.save(recipe);

            return ingredientToIngredientCommand.convert(
                savedRecipe.getIngredients()
                           .stream()
                           .filter(ingredient -> ingredient.getId().equals(id))
                           .findFirst()
                           .orElse(null)
            );
        }
    }

}
