package guru.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private static final String RECIPE_STR = "recipe";
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public String showRecipe(@PathVariable Long id, Model model) {
        var recipe = recipeService.findById(id);
        model.addAttribute(RECIPE_STR, recipe);

        return "recipes/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        var recipe = new RecipeCommand();
        model.addAttribute(RECIPE_STR, recipe);

        return "recipes/form";
    }

    @GetMapping("/{id}/edit")
    public String editRecipe(@PathVariable Long id, Model model) {
        var recipe = recipeService.findCommandById(id);
        model.addAttribute(RECIPE_STR, recipe);

        return "recipes/form";
    }

    @PostMapping
    public String createOrUpdateRecipe(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveCommand(command);

        return "redirect:/recipes/" + savedCommand.getId();
    }

    @GetMapping("/{id}/delete")
    public String destroyRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);

        return "redirect:/index";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception.");
        log.error("Message: " + exception.getMessage());

        var modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
