package org.example.turistguide_2;

import org.example.turistguide_2.model.TouristAttraction;
import org.example.turistguide_2.repository.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TouristRepositoryTest {

    private TouristRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TouristRepository();
    }

    @Test
    void getAttractions() {

        List<TouristAttraction> expected = new ArrayList<>(List.of(
                new TouristAttraction("Eiffel Tower", "Paris", "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France.", List.of("Historical", "Free")),
                new TouristAttraction("Statue of Liberty", "New York", "The Statue of Liberty is a colossal neoclassical sculpture on Liberty Island in New York Harbor in New York City.", List.of("Historical")),
                new TouristAttraction("Tivoli", "Copenhagen", "A bustling themepark, in the middle of Copenhagen.", List.of("Historical", "Food")),
                new TouristAttraction("Machu Picchu", "Urubambu", "Machu Picchu is an Incan citadel set high in the Andes Mountains in Peru, above the Urubamba River valley.", List.of("Historical", "Nature")),
                new TouristAttraction("Taj Mahal", "Agra", "The Taj Mahal is an ivory-white marble mausoleum on the right bank of the Yamuna river in the Indian city of Agra.", List.of("Historical", "Architecture")),
                new TouristAttraction("MOMA", "New York", "The Museum of Modern Art (MoMA) is an art museum located in Midtown Manhattan, New York City.", List.of("Free", "Art"))
        ));

        List<TouristAttraction> actual = repository.getAttractions();

        assertEquals(expected, actual);
    }

    @Test
    void getAttraction() {
        TouristAttraction expected = new TouristAttraction("Eiffel Tower", "Paris", "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France.", List.of("Historical", "Free"));
        TouristAttraction actual = repository.getAttraction("Eiffel Tower");
        assertEquals(expected, actual);
    }

    @Test
    void addAttraction() {
        TouristAttraction attraction = new TouristAttraction("Test Attraction", "Somewhere", "Some description", List.of("Historical", "Free"));
        repository.addAttraction(attraction);
        assertEquals(attraction, repository.getAttraction("Test Attraction"));
    }

    @Test
    void updateAttraction() {
        repository.getAttractions().add(new TouristAttraction("Test Attraction", "Somewhere", "Some description", List.of("Historical", "Free")));
        repository.getAttraction("Test Attraction").setDescription("New description");
        repository.updateAttraction(repository.getAttraction("Test Attraction"));
        assertEquals("New description", repository.getAttraction("Test Attraction").getDescription());
    }

    @Test
    void deleteAttraction() {
        repository.deleteAttraction("Eiffel Tower");
        assertNull(repository.getAttraction("Eiffel Tower"));
    }


}
