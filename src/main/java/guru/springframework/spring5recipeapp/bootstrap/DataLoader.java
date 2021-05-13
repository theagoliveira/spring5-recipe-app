package guru.springframework.spring5recipeapp.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.domain.Difficulty;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository,
                      CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        var tspUom = unitOfMeasureRepository.findByDescription("teaspoon").get();
        var tbspUom = unitOfMeasureRepository.findByDescription("tablespoon").get();
        var dashUom = unitOfMeasureRepository.findByDescription("dash").get();

        var mexicanCategory = categoryRepository.findByDescription("Mexican").get();

        var perfectGuacamole = new Recipe();
        perfectGuacamole.setDescription(
            "The best guacamole keeps it simple: just ripe avocados, salt, a squeeze of lime, " +
                "onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your " +
                "next party or spoon it on top of tacos for an easy dinner upgrade."
        );
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setSource("Simply Recipes");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setDirections(
            "<li>Prepare a gas or charcoal grill for medium-high, direct heat</li>" +
                "<li>Make the marinade and coat the chicken:<p>In a large bowl, stir together " +
                "the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. " +
                "Stir in the orange juice and olive oil to make a loose paste. Add the " +
                "chicken to the bowl and toss to coat all over.</p>" +
                "<p>Set aside to marinate while the grill heats and you prepare the rest of " +
                "the toppings.</p>" +
                "</li>" +
                "<li>Grill the chicken:<p>Grill the chicken for 3 to 4 minutes per side, or " +
                "until a thermometer inserted into the thickest part of the meat registers " +
                "165F. Transfer to a plate and rest for 5 minutes.</p>" +
                "</li>" +
                "<li>Warm the tortillas:<p>Place each tortilla on the grill or on a hot, dry " +
                "skillet over medium-high heat. As soon as you see pockets of the air " +
                "start to puff up in the tortilla, turn it with tongs and heat for a few " +
                "seconds on the other side.</p>" +
                "<p>Wrap warmed tortillas in a tea towel to keep them warm until serving." +
                "</p>" +
                "</li>" +
                "<li>Assemble the tacos:<p>Slice the chicken into strips. On each tortilla, " +
                "place a small handful of arugula. Top with chicken slices, sliced " +
                "avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned " +
                "sour cream. Serve with lime wedges.</p>" +
                "</li>"
        );
        perfectGuacamole.setDifficulty(Difficulty.EASY);

        var ripeAvocados = new Ingredient();
        ripeAvocados.setAmount(BigDecimal.valueOf(2));
        ripeAvocados.setDescription("ripe avocados");
        ripeAvocados.setUom(null);

        var salt = new Ingredient();
        salt.setAmount(BigDecimal.valueOf(0.25));
        salt.setDescription("salt, more to taste");
        salt.setUom(tspUom);

        var freshLimeJuice = new Ingredient();
        freshLimeJuice.setAmount(BigDecimal.valueOf(1));
        freshLimeJuice.setDescription("fresh lime juice or lemon juice");
        freshLimeJuice.setUom(tbspUom);

        var mincedRedOnion = new Ingredient();
        mincedRedOnion.setAmount(BigDecimal.valueOf(1));
        mincedRedOnion.setDescription("minced red onion or thinly sliced green onion");
        mincedRedOnion.setUom(tbspUom);

        var serranoChiles = new Ingredient();
        serranoChiles.setAmount(BigDecimal.valueOf(2));
        serranoChiles.setDescription("serrano chiles, stems and seeds removed, minced");
        serranoChiles.setUom(null);

        var cilantro = new Ingredient();
        cilantro.setAmount(BigDecimal.valueOf(2));
        cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
        cilantro.setUom(tbspUom);

        var freshlyGratedBlackPepper = new Ingredient();
        freshlyGratedBlackPepper.setAmount(BigDecimal.valueOf(1));
        freshlyGratedBlackPepper.setDescription("freshly grated black pepper");
        freshlyGratedBlackPepper.setUom(dashUom);

        var ripeTomato = new Ingredient();
        ripeTomato.setAmount(BigDecimal.valueOf(0.5));
        ripeTomato.setDescription("ripe tomato, seeds and pulp removed, chopped");
        ripeTomato.setUom(null);

        var redRadishes = new Ingredient();
        redRadishes.setAmount(null);
        redRadishes.setDescription("red radishes or jicama, to garnish");
        redRadishes.setUom(null);

        var tortillaChips = new Ingredient();
        tortillaChips.setAmount(null);
        tortillaChips.setDescription("tortilla chips, to serve");
        tortillaChips.setUom(null);

        perfectGuacamole.addIngredient(ripeAvocados);
        perfectGuacamole.addIngredient(salt);
        perfectGuacamole.addIngredient(freshLimeJuice);
        perfectGuacamole.addIngredient(mincedRedOnion);
        perfectGuacamole.addIngredient(serranoChiles);
        perfectGuacamole.addIngredient(cilantro);
        perfectGuacamole.addIngredient(freshlyGratedBlackPepper);
        perfectGuacamole.addIngredient(ripeTomato);
        perfectGuacamole.addIngredient(redRadishes);
        perfectGuacamole.addIngredient(tortillaChips);

        perfectGuacamole.getCategories().add(mexicanCategory);

        recipeRepository.save(perfectGuacamole);
    }

}
