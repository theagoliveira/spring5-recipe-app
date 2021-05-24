package guru.springframework.spring5recipeapp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.services.RecipeServiceImpl;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    private static final Long ID = 2L;
    private static final String DESCRIPTION = "description";

    @Mock
    RecipeServiceImpl recipeService;

    @Mock
    Model model;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                                 .setControllerAdvice(new ControllerExceptionHandler())
                                 .build();
    }

    @Test
    void showRecipe() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        // when
        when(recipeService.findById(1L)).thenReturn(recipe);

        // then
        mockMvc.perform(get("/recipes/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipes/show"))
               .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void showRecipeNotFound() throws Exception {
        // when
        when(recipeService.findById(1L)).thenThrow(NotFoundException.class);

        // then
        mockMvc.perform(get("/recipes/1"))
               .andExpect(view().name("404"))
               .andExpect(status().isNotFound());
    }

    @Test
    void showRecipeWrongIDFormat() throws Exception {
        // then
        mockMvc.perform(get("/recipes/abc"))
               .andExpect(view().name("400"))
               .andExpect(status().isBadRequest());
    }

    @Test
    void newRecipe() throws Exception {
        // then
        mockMvc.perform(get("/recipes/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipes/form"))
               .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void editRecipe() throws Exception {
        // given
        var command = new RecipeCommand();
        command.setId(ID);

        // when
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        // then
        mockMvc.perform(get("/recipes/2/edit"))
               .andExpect(status().isOk())
               .andExpect(view().name("recipes/form"))
               .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void createOrUpdateRecipe() throws Exception {
        // given
        var command = new RecipeCommand();
        command.setId(ID);

        // when
        when(recipeService.saveCommand(any())).thenReturn(command);

        // then
        mockMvc.perform(
            post("/recipes").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("id", "")
                            .param("description", DESCRIPTION)
        ).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipes/2"));
    }

    @Test
    void destroyRecipe() throws Exception {
        // then
        mockMvc.perform(get("/recipes/1/delete"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/index"));

        verify(recipeService).deleteById(anyLong());
    }

}
