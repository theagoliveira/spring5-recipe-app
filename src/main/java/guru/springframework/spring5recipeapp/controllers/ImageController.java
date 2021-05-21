package guru.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.spring5recipeapp.services.ImageService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/recipes/{recipeId}")
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/image")
    public String edit(@PathVariable Long recipeId, Model model) {
        var recipeCommand = recipeService.findCommandById(recipeId);
        model.addAttribute("recipe", recipeCommand);

        return "recipes/images/form";
    }

    @PostMapping("/image")
    public String upload(@PathVariable Long recipeId,
                         @RequestParam("imagefile") MultipartFile file) {
        log.info("Received a file.");
        imageService.saveFile(recipeId, file);

        return "redirect:/recipes/" + recipeId;
    }

}
