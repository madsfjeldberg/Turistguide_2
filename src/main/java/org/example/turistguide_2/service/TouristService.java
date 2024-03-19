package org.example.turistguide_2.service;

import org.example.turistguide_2.model.TouristAttraction;
import org.example.turistguide_2.repository.TouristRepositorySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepositorySQL repository;

    @Autowired
    public TouristService(TouristRepositorySQL repository) {
        this.repository = repository;
    }

    public TouristAttraction getAttraction(String name) {
        return repository.getAttraction(name);
    }

    public List<TouristAttraction> getTouristAttractions() {
        return repository.getAttractions();
    }

    public void addTouristAttraction(TouristAttraction attraction) {
        repository.addAttraction(attraction);
    }

    public void updateTouristAttraction(TouristAttraction attraction) {
        repository.updateAttraction(attraction);
    }

    public void deleteTouristAttraction(String attraction) {
        repository.deleteAttraction(attraction);
    }

    public List<String> getTags() {
        return repository.getTags();
    }
}
