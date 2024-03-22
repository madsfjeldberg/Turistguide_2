DROP DATABASE IF EXISTS tourist_db;

CREATE DATABASE tourist_db;

USE tourist_db;

DROP TABLE IF EXISTS tourist_attraction;
DROP TABLE IF EXISTS attraction_tags;
DROP TABLE IF EXISTS all_tags;

CREATE TABLE tourist_attraction (
    attraction_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE attraction_tags (
    attraction_id INT,
    tag_id INT NOT NULL,
    FOREIGN KEY (attraction_id) REFERENCES tourist_attraction(attraction_id)
);

CREATE TABLE all_tags (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    value VARCHAR(255) NOT NULL
);

INSERT INTO all_tags (value) VALUES
    ("Historical"),
    ("Free"),
    ("Nature"),
    ("Art"),
    ("Food"),
    ("Architecture");

INSERT INTO tourist_attraction (name, location, description) VALUES
    ("Eiffel Tower", "Paris", "The Eiffel Tower is a wrought-iron lattice tower on the Champ de Mars in Paris, France."),
    ("Statue of Liberty", "New York", "The Statue of Liberty is a colossal neoclassical sculpture on Liberty Island in New York Harbor in New York City."),
    ("Tivoli", "Copenhagen", "A bustling theme park, in the middle of Copenhagen."),
    ("Machu Picchu", "Urubambu", "Machu Picchu is an Incan citadel set high in the Andes Mountains in Peru, above the Urubamba River valley."),
    ("Taj Mahal", "Agra", "The Taj Mahal is an ivory-white marble mausoleum on the right bank of the Yamuna river in the Indian city of Agra."),
    ("MOMA", "New York", "The Museum of Modern Art (MoMA) is an art museum located in Midtown Manhattan, New York City.");

INSERT INTO attraction_tags (attraction_id, tag_id) VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 1),
    (3, 5),
    (4, 1),
    (4, 3),
    (5, 1),
    (5, 6),
    (6, 2),
    (6, 4);
