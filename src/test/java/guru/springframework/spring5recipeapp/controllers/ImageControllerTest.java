package guru.springframework.spring5recipeapp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.ImageService;
import guru.springframework.spring5recipeapp.services.RecipeService;

class ImageControllerTest {

    private static final Long COMMAND_ID = 1L;

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void edit() throws Exception {
        // given
        var recipeCommand = new RecipeCommand();
        recipeCommand.setId(COMMAND_ID);

        // when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipes/1/image"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("recipe"));

        // then
        verify(recipeService).findCommandById(anyLong());
    }

    @Test
    void upload() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile(
            "imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes()
        );

        // when
        mockMvc.perform(multipart("/recipes/1/image").file(multipartFile))
               .andExpect(status().is3xxRedirection())
               .andExpect(header().string("Location", "/recipes/1"));

        // then
        verify(imageService).saveFile(anyLong(), any());
    }

}
