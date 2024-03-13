package org.example.turistguide_2.repository;

import org.example.turistguide_2.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TouristRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public TouristRepository() throws SQLException {

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                System.out.println("Connecting...");
                System.out.println("Driver name is: " + conn.getMetaData());
                System.out.println("Connection established.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

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
                t.setLocation(attraction.getLocation());
                t.setTags(attraction.getTags());
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
}
