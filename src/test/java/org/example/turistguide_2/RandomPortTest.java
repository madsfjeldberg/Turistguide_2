package org.example.turistguide_2;


import org.example.turistguide_2.controller.TouristController;
import org.example.turistguide_2.repository.TouristRepository;
import org.example.turistguide_2.service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RandomPortTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TouristController controller;

    @Autowired
    private TouristService service;

    @Autowired
    private TouristRepository repository;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(service).isNotNull();
        assertThat(repository).isNotNull();
    }

}
