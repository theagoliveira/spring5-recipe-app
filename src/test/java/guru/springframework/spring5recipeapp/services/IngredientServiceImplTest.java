package guru.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

class IngredientServiceImplTest {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 2L;
    private static final Long COMMAND_ID = 3L;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();

    IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(
        unitOfMeasureToUnitOfMeasureCommand
    );
    IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(
        unitOfMeasureCommandToUnitOfMeasure
    );

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(
            recipeRepository, unitOfMeasureRepository, ingredientToIngredientCommand,
            ingredientCommandToIngredient
        );
    }

    @Test
    void findCommandByIdAndRecipeId() {
        // given
        var ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);

        var recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.getIngredients().add(ingredient);

        ingredient.setRecipe(recipe);

        var returnCommand = new IngredientCommand();
        returnCommand.setId(INGREDIENT_ID);
        returnCommand.setRecipeId(RECIPE_ID);

        // when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        IngredientCommand command = ingredientService.findCommandByIdAndRecipeId(
            INGREDIENT_ID, RECIPE_ID
        );

        // then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getRecipeId());
        assertEquals(INGREDIENT_ID, command.getId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void saveCommand() {
        // given
        var command = new IngredientCommand();
        command.setId(INGREDIENT_ID);
        command.setRecipeId(RECIPE_ID);

        Optional<Recipe> optionalRecipe = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(INGREDIENT_ID);

        // when
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        IngredientCommand savedCommand = ingredientService.saveCommand(command);

        // then
        assertEquals(INGREDIENT_ID, savedCommand.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any());
    }

}
