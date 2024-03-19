package org.example.turistguide_2.repository;

import org.example.turistguide_2.model.TouristAttraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepositorySQL {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private final DataSource dataSource;

    @Autowired
    public TouristRepositorySQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private TouristAttraction findAttractionInList(List<TouristAttraction> attractions, int attractionID ) {
        for (TouristAttraction attraction : attractions) {
            if (attraction.getPid() == attractionID) {
                return attraction;
            }
        }
        return null;

    }

    public List<TouristAttraction> getAttractions() {
        List<TouristAttraction> attractions = new ArrayList<>();

        // joiner alle tre lister sammen.
        // dog kommer der duplicates af attractions, hvis de har flere tags.
        String sql = "SELECT attractions.*, tags.tag_id, all_tags.value " +
                     "FROM tourist_attraction AS attractions " +
                     "LEFT JOIN attraction_tags AS tags ON attractions.attraction_id = tags.attraction_id " +
                     "LEFT JOIN all_tags ON tags.tag_id = all_tags.tag_id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int attractionId = rs.getInt("attraction_id");
                TouristAttraction attraction = findAttractionInList(attractions, attractionId); // hvis attraction ikke findes, lav en ny.
                if (attraction == null) {                                                       // ellers hop til 68, og tilføj tag til attraction.
                    attraction = new TouristAttraction();
                    attraction.setPid(attractionId);
                    attraction.setName(rs.getString("name"));
                    attraction.setLocation(rs.getString("location"));
                    attraction.setDescription(rs.getString("description"));
                    attraction.setTags(new ArrayList<>());
                    attractions.add(attraction);
                }

                String tagValue = rs.getString("value"); // hvis attraction allerede findes, tilføjes der kun tagValue til attraction.
                if (tagValue != null) {
                    attraction.getTags().add(tagValue);
            }

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting attractions", e);
        }
        return attractions;
    }

    public String getTagValueFromId(int tagID) {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT value FROM all_tags WHERE tag_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tagID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("value");
        } catch (SQLException e) {
            throw new RuntimeException("Error getting attraction values", e);
        }
    }

    public List<String> getAttractionTagsById(int attractionId) {
        String sql = "SELECT tag_id FROM attraction_tags WHERE attraction_id = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, attractionId);
            try (ResultSet rs = ps.executeQuery()) {
                List<String> tags = new ArrayList<>();
                while (rs.next()) {
                    String tag = getTagValueFromId(rs.getInt("tag_id"));
                    if (tag != null) {
                        tags.add(tag);
                    }
                }
                return tags;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting attraction tags", e);
        }
    }

    public TouristAttraction getAttraction(String name) {
        String sql = "SELECT * FROM tourist_attraction WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? getAttractionFromResultSet(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting attraction by name", e);
        }
    }

    private TouristAttraction getAttractionFromResultSet(ResultSet rs) throws SQLException {
        TouristAttraction attraction = new TouristAttraction();
        attraction.setPid(rs.getInt("attraction_id"));
        attraction.setName(rs.getString("name"));
        attraction.setLocation(rs.getString("location"));
        attraction.setDescription(rs.getString("description"));
        attraction.setTags(getAttractionTagsById(attraction.getPid()));
        return attraction;
    }

    public void addAttraction(TouristAttraction attraction) {
        String sql = "INSERT INTO tourist_attraction (name, location, description) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, attraction.getName());
            ps.setString(2, attraction.getLocation());
            ps.setString(3, attraction.getDescription());
            ps.executeUpdate();
            addAttractionTags(attraction);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding attraction", e);
        }
    }

    public void deleteAttractionTags(TouristAttraction attraction) {
        String sql = "DELETE FROM attraction_tags WHERE attraction_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, getAttractionIdFromName(attraction.getName()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting attraction tags", e);
        }
    }

    public void addAttractionTags(TouristAttraction attraction) {
        String sql = "INSERT INTO attraction_tags (attraction_id, tag_id) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String tag : attraction.getTags()) {
                int tagId = getTagIdFromValue(tag);
                if (tagId != -1) {
                    ps.setInt(1, getAttractionIdFromName(attraction.getName()));
                    ps.setInt(2, tagId);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding attraction tags", e);
        }
    }

    public int getAttractionIdFromName(String name) {
        String sql = "SELECT attraction_id FROM tourist_attraction WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("attraction_id") : -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting attraction id from name", e);
        }
    }

    public int getTagIdFromValue(String value) {
        String sql = "SELECT tag_id FROM all_tags WHERE value = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("tag_id") : -1;
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error getting tag id from value", e);
        }
    }

    public void updateAttraction(TouristAttraction attraction) {
        String sql = "UPDATE tourist_attraction SET location = ?, description = ? WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, attraction.getLocation());
            ps.setString(2, attraction.getDescription());
            ps.setString(3, attraction.getName());
            ps.executeUpdate();
            deleteAttractionTags(attraction);
            addAttractionTags(attraction);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating attraction", e);
        }
    }

    public void deleteAttraction(String attractionName) {
        String sql = "DELETE FROM tourist_attraction WHERE name = ?";
        try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, attractionName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting attraction", e);
        }
    }

    public List<String> getTags() {
        String sql = "SELECT value FROM all_tags";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<String> tags = new ArrayList<>();
            while (rs.next()) {
                tags.add(rs.getString("value"));
            }
            return tags;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting tags", e);
        }
    }
}
