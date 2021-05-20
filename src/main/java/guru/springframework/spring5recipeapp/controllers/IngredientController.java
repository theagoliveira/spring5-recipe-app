package guru.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import guru.springframework.spring5recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipes/{recipeId}/ingredients")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping({"", "/", "/index"})
    public String index(@PathVariable Long recipeId, Model model) {
        log.debug("Get ingredients list for recipe with id " + recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));
        return "recipes/ingredients/index";
    }

    @GetMapping("/{id}")
    public String showIngredient(@PathVariable Long id, @PathVariable Long recipeId, Model model) {
        model.addAttribute(
            "ingredient", ingredientService.findCommandByIdAndRecipeId(id, recipeId)
        );
        model.addAttribute("recipeName", recipeService.findCommandById(recipeId).getName());
        return "recipes/ingredients/show";
    }

    @GetMapping("/{id}/edit")
    public String editIngredient(@PathVariable Long id, @PathVariable Long recipeId, Model model) {
        model.addAttribute(
            "ingredient", ingredientService.findCommandByIdAndRecipeId(id, recipeId)
        );
        model.addAttribute("uoms", unitOfMeasureService.findAllCommands());
        return "recipes/ingredients/form";
    }

    @PostMapping("/")
    public String createOrUpdateIngredient(@ModelAttribute IngredientCommand command,
                                           @PathVariable Long recipeId) {
        IngredientCommand savedCommand = ingredientService.saveCommand(command);

        return "redirect:/recipes/" + recipeId + "/ingredients/" + savedCommand.getId();
    }

}
