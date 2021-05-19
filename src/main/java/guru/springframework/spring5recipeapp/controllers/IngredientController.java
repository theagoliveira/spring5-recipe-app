package guru.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipes/{recipeId}/ingredients")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping({"", "/", "/index"})
    public String index(@PathVariable Long recipeId, Model model) {
        log.debug("Get ingredients list for recipe with id " + recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipes/ingredients/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, @PathVariable Long recipeId, Model model) {
        model.addAttribute(
            "ingredient", ingredientService.findCommandByIdAndRecipeId(id, recipeId)
        );
        model.addAttribute("recipeName", recipeService.findCommandById(recipeId).getName());
        return "recipes/ingredients/show";
    }

}
