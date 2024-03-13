package org.example.turistguide_2.repository;

import org.example.turistguide_2.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TouristRepositorySQL {

     private Connection conn;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public TouristRepositorySQL() throws SQLException {

        conn = DriverManager.getConnection(url, username, password);
        if (conn != null) {
            System.out.println("Connecting...");
            System.out.println("Driver name is: " + conn.getMetaData());
            System.out.println("Connection established.");
        }
    }

    public List<TouristAttraction> getAttractions() {
        return null;
    }

    public TouristAttraction getAttraction(String name) {
        return null;
    }

    public TouristAttraction addAttraction(TouristAttraction attraction) {
        return attraction;
    }

    public TouristAttraction updateAttraction(TouristAttraction attraction) {
        return null;
    }

    public TouristAttraction deleteAttraction(String attraction) {
        return null;
    }
}
