package guru.springframework.spring5recipeapp.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;

class IngredientControllerTest {

    private static final String RECIPE_NAME = "name";

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void index() throws Exception {
        // given
        var command = new RecipeCommand();

        // when
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        mockMvc.perform(get("/recipes/1/ingredients"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipes/ingredients/index"))
               .andExpect(model().attributeExists("recipe"));

        // then
        verify(recipeService).findCommandById(anyLong());
    }

    @Test
    void show() throws Exception {
        // given
        var ingredientCommand = new IngredientCommand();
        var recipeCommand = new RecipeCommand();
        recipeCommand.setName(RECIPE_NAME);

        // when
        when(ingredientService.findCommandByIdAndRecipeId(anyLong(), anyLong())).thenReturn(
            ingredientCommand
        );
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipes/1/ingredients/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipes/ingredients/show"))
               .andExpect(model().attributeExists("ingredient"))
               .andExpect(model().attributeExists("recipeName"));

        // then
        verify(ingredientService).findCommandByIdAndRecipeId(anyLong(), anyLong());
        verify(recipeService).findCommandById(anyLong());
    }

}
