package org.example.turistguide_2.repository;

import org.example.turistguide_2.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class TouristRepositorySQL {


    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<TouristAttraction> getAttractions() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM tourist_attraction";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
