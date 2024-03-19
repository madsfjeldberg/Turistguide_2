package org.example.turistguide_2;

import org.example.turistguide_2.controller.TouristController;
import org.example.turistguide_2.service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.example.turistguide_2.model.TouristAttraction;
import org.junit.jupiter.api.BeforeEach;



import java.util.List;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@WebMvcTest(TouristController.class)
public class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService service;

    @BeforeEach
    void setup() {
        List<TouristAttraction> attractions = Arrays.asList(
                new TouristAttraction("Eiffel Tower", "Paris", "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France.", List.of("Historical", "Free")),
                new TouristAttraction("Statue of Liberty", "New York", "The Statue of Liberty is a colossal neoclassical sculpture on Liberty Island in New York Harbor in New York City.", List.of("Historical")),
                new TouristAttraction("Tivoli", "Copenhagen", "A bustling themepark, in the middle of Copenhagen.", List.of("Historical", "Food")),
                new TouristAttraction("Machu Picchu", "Urubambu", "Machu Picchu is an Incan citadel set high in the Andes Mountains in Peru, above the Urubamba River valley.", List.of("Historical", "Nature")),
                new TouristAttraction("Taj Mahal", "Agra", "The Taj Mahal is an ivory-white marble mausoleum on the right bank of the Yamuna river in the Indian city of Agra.", List.of("Historical", "Architecture")),
                new TouristAttraction("MOMA", "New York", "The Museum of Modern Art (MoMA) is an art museum located in Midtown Manhattan, New York City.", List.of("Free", "Art"))
        );

        when(service.getTouristAttractions()).thenReturn(attractions);
        when(service.getAttraction(anyString())).thenReturn(attractions.get(0));
    }

    @Test
    void getAttractions() throws Exception {
        mockMvc.perform(get("/attractions"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("attractionList"));
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(get("/attractions/add"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("add"));
    }

    @Test
    void getTags() throws Exception {
        mockMvc.perform(get("/attractions/Eiffel Tower/tags"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("tags"));
    }

    @Test
    void editAttraction() throws Exception {
        mockMvc.perform(get("/attractions/Eiffel Tower/edit"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("edit"));
    }

    @Test
    void getAttraction() throws Exception {
        mockMvc.perform(get("/attractions/Eiffel Tower"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("attraction"));
    }

    
}
