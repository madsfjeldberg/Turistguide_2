package org.example.turistguide_2;

import org.example.turistguide_2.controller.TouristController;
import org.example.turistguide_2.repository.TouristRepository;
import org.example.turistguide_2.service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TuristGuide2ApplicationTests {

    @Autowired
    private TouristController controller;
    @Autowired
    private TouristRepository repository;
    @Autowired
    private TouristService service;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(repository).isNotNull();
        assertThat(service).isNotNull();
    }

}
