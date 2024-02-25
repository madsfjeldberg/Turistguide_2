package org.example.turistguide_2.service;

import org.example.turistguide_2.model.TouristAttraction;
import org.example.turistguide_2.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    TouristRepository repo;

    public TouristService(TouristRepository repo) {
        this.repo = repo;
    }

    public TouristAttraction getAttraction(String name) {
        return repo.getAttraction(name);
    }

    public List<TouristAttraction> getTouristAttractions() {
        return repo.getAttractions();
    }

    public void addTouristAttraction(TouristAttraction attraction) {
        repo.addAttraction(attraction);
    }

    public void updateTouristAttraction(TouristAttraction attraction) {
        repo.updateAttraction(attraction);
    }

    public void deleteTouristAttraction(String attraction) {
        repo.deleteAttraction(attraction);
    }
}
