package org.example.turistguide_2;

import org.example.turistguide_2.controller.TouristController;
import org.example.turistguide_2.service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TouristController.class)
public class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService service;

    @Test
    void getTouristAttractions() throws Exception {
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

    @Test
    void saveAttraction() throws Exception {
        mockMvc.perform(get("/attractions/save"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("redirect:/attractions"));
    }

    
}
