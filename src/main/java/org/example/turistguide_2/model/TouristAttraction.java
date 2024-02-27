package org.example.turistguide_2.model;

import java.util.List;
import java.util.Objects;

public class TouristAttraction {

    private String name;
    private String location;
    private String description;
    private List<String> tags;

    public TouristAttraction(String name, String location, String description, List<String> tags) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.tags = tags;
    }

    public TouristAttraction() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TouristAttraction that = (TouristAttraction) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(location, that.location) &&
                Objects.equals(description, that.description) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, description, tags);
    }
}
