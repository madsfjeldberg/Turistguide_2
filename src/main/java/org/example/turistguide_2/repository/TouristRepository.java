package org.example.turistguide_2.repository;
import org.example.turistguide_2.TagsENUM;

import org.example.turistguide_2.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private List<TouristAttraction> attractions;

    public TouristRepository() {

        attractions = new ArrayList<>(List.of(
                new TouristAttraction("Eiffel Tower", "Paris", "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France.", List.of("Historical", "Free")),
                new TouristAttraction("Statue of Liberty", "New York", "The Statue of Liberty is a colossal neoclassical sculpture on Liberty Island in New York Harbor in New York City.", List.of("Historical")),
                new TouristAttraction("Tivoli", "Copenhagen", "A bustling themepark, in the middle of Copenhagen.", List.of("Historical", "Food")),
                new TouristAttraction("Machu Picchu", "Urubambu", "Machu Picchu is an Incan citadel set high in the Andes Mountains in Peru, above the Urubamba River valley.", List.of("Historical", "Nature")),
                new TouristAttraction("Taj Mahal", "Agra", "The Taj Mahal is an ivory-white marble mausoleum on the right bank of the Yamuna river in the Indian city of Agra.", List.of("Historical", "Architecture")),
                new TouristAttraction("MOMA", "New York", "The Museum of Modern Art (MoMA) is an art museum located in Midtown Manhattan, New York City.", List.of("Free", "Art"))
        ));
    }

    public List<TouristAttraction> getAttractions() {
        return attractions;
    }

    public TouristAttraction getAttraction(String name) {
        return attractions.stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
    }

    public TouristAttraction addAttraction(TouristAttraction attraction) {
        attractions.add(attraction);
        return attraction;
    }

    public TouristAttraction updateAttraction(TouristAttraction attraction) {
        for (TouristAttraction t : attractions) {
            if (t.getName().equals(attraction.getName())) {
                t.setDescription(attraction.getDescription());
                return t;
            }
        }
        return null;
    }

    public TouristAttraction deleteAttraction(String attraction) {
        for (TouristAttraction t : attractions) {
            if (t.getName().equals(attraction)) {
                attractions.remove(t);
                return t;
            }
        }
        return null;
    }

    public List<String> getTags() {
        List<String> tags = new ArrayList<>();
        for (TouristAttraction t : attractions) {
            tags.addAll(t.getTags());
        }
        return tags;
    }
}
