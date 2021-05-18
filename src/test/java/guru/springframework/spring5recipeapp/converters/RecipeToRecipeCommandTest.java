package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.domain.Difficulty;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Notes;
import guru.springframework.spring5recipeapp.domain.Recipe;

class RecipeToRecipeCommandTest {

    public static final Long ID = 1L;
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final Integer PREP_TIME = 1;
    public static final Integer COOK_TIME = 2;
    public static final Integer SERVINGS = 3;
    public static final String SOURCE = "source";
    public static final String URL = "url";
    public static final String DIRECTIONS = "directions";
    public static final Difficulty DIFFICULTY = Difficulty.HARD;
    public static final Long NOTES_ID = 5L;
    public static final String RECIPE_NOTES = "recipeNotes";
    public static final Notes NOTES = new Notes(NOTES_ID, RECIPE_NOTES);
    public static final Long INGREDIENT1_ID = 8L;
    public static final String INGREDIENT1_DESCRIPTION = "ingredient1Description";
    public static final BigDecimal INGREDIENT1_AMOUNT = BigDecimal.valueOf(1.5);
    public static final Ingredient INGREDIENT1 = new Ingredient(
        INGREDIENT1_ID, INGREDIENT1_DESCRIPTION, INGREDIENT1_AMOUNT
    );
    public static final Long INGREDIENT2_ID = 9L;
    public static final String INGREDIENT2_DESCRIPTION = "ingredient2Description";
    public static final BigDecimal INGREDIENT2_AMOUNT = BigDecimal.valueOf(3.5);
    public static final Ingredient INGREDIENT2 = new Ingredient(
        INGREDIENT2_ID, INGREDIENT2_DESCRIPTION, INGREDIENT2_AMOUNT
    );
    public static final Long CATEGORY1_ID = 10L;
    public static final String CATEGORY1_DESCRIPTION = "category1Description";
    public static final Category CATEGORY1 = new Category(CATEGORY1_ID, CATEGORY1_DESCRIPTION);
    public static final Long CATEGORY2_ID = 11L;
    public static final String CATEGORY2_DESCRIPTION = "category2Description";
    public static final Category CATEGORY2 = new Category(CATEGORY2_ID, CATEGORY2_DESCRIPTION);

    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(
            new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
            new CategoryToCategoryCommand(), new NotesToNotesCommand()
        );
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setName(NAME);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setNotes(NOTES);
        recipe.getIngredients().add(INGREDIENT1);
        recipe.getIngredients().add(INGREDIENT2);
        recipe.getCategories().add(CATEGORY1);
        recipe.getCategories().add(CATEGORY2);

        // when
        RecipeCommand recipeCommand = converter.convert(recipe);

        // then
        assertNotNull(recipeCommand);
        assertNotNull(recipeCommand.getNotes());
        assertNotNull(recipeCommand.getIngredients());
        assertNotNull(recipeCommand.getCategories());
        assertEquals(ID, recipeCommand.getId());
        assertEquals(NAME, recipeCommand.getName());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(RECIPE_NOTES, recipeCommand.getNotes().getRecipeNotes());
        assertEquals(2, recipeCommand.getIngredients().size());
        assertEquals(2, recipeCommand.getCategories().size());
    }

    @Test
    void convertWithoutNotesAndSets() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setName(NAME);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        // when
        RecipeCommand recipeCommand = converter.convert(recipe);

        // then
        assertNotNull(recipeCommand);
        assertNull(recipeCommand.getNotes());
        assertNotNull(recipeCommand.getIngredients());
        assertNotNull(recipeCommand.getCategories());
        assertEquals(ID, recipeCommand.getId());
        assertEquals(NAME, recipeCommand.getName());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(0, recipeCommand.getIngredients().size());
        assertEquals(0, recipeCommand.getCategories().size());
    }

}
