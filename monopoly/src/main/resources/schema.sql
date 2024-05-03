DROP TABLE IF EXISTS players;

CREATE TABLE player (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(30) NOT NULL,
    player_position INT DEFAULT 0,
    player_balance FLOAT DEFAULT 0,
    jail_turns INT DEFAULT 0,
    has_lost BOOLEAN DEFAULT FALSE
);