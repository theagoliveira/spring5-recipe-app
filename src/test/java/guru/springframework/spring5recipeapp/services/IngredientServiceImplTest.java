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
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;

class IngredientServiceImplTest {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 2L;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(
            recipeRepository, ingredientToIngredientCommand
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

        var returnCommand = new IngredientCommand();
        returnCommand.setId(INGREDIENT_ID);
        returnCommand.setRecipeId(RECIPE_ID);

        // when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(ingredientToIngredientCommand.convert(any())).thenReturn(returnCommand);
        IngredientCommand command = ingredientService.findCommandByIdAndRecipeId(
            INGREDIENT_ID, RECIPE_ID
        );

        // then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getRecipeId());
        assertEquals(INGREDIENT_ID, command.getId());
        verify(recipeRepository).findById(anyLong());
        verify(ingredientToIngredientCommand).convert(any());

    }

}
