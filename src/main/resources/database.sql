CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE times(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    time_started INT NOT NULL,
    time_stopped INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE wages(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    wage DECIMAL(6,2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
)