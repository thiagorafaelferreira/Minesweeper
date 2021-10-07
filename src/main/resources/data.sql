DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS game_match;
DROP TABLE IF EXISTS mine_field;

CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  username VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  birth_date DATE NOT NULL,
  password VARCHAR(250) NOT NULL
);

CREATE TABLE game_match (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id VARCHAR(250) NOT NULL,
  status VARCHAR(20) NOT NULL
);

CREATE TABLE mine_field (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id VARCHAR(250) NOT NULL,
  row_position INT NOT NULL,
  column_position INT NOT NULL,
  flaged BOOLEAN NOT NULL,
  mine BOOLEAN NOT NULL,
  mines_arround INT NOT NULL,
  clicked BOOLEAN NOT NULL
);

INSERT INTO users (name, username, email, birth_date, password) VALUES
  ('Thiago', 'thiagueira', 'thiago@gmail.com', '1987-01-28', '09876'),
  ('Gabriela', 'gabriela', 'gabriela@gmail.com', '1988-02-10', '01234');

INSERT INTO game_match (user_id, status) VALUES
  ('1fasdf2', 'PLAYING');

INSERT INTO mine_field (user_id, row_position, column_position, flaged, mine, mines_arround, clicked) VALUES
  ('1fasdf2', 0, 0, false, false, 0, false),
  ('1fasdf2', 0, 1, false, false, 0, false),
  ('1fasdf2', 0, 2, false, false, 0, false),
  ('1fasdf2', 0, 3, false, false, 0, false),
  ('1fasdf2', 1, 0, false, true, 0, false),
  ('1fasdf2', 1, 1, false, false, 0, false),
  ('1fasdf2', 1, 2, false, false, 0, false),
  ('1fasdf2', 1, 3, false, false, 0, false),
  ('1fasdf2', 2, 0, false, false, 0, false),
  ('1fasdf2', 2, 1, false, true, 0, false),
  ('1fasdf2', 2, 2, false, false, 0, false),
  ('1fasdf2', 2, 3, false, false, 0, false),
  ('1fasdf2', 3, 0, false, true, 0, false),
  ('1fasdf2', 3, 1, false, false, 0, false),
  ('1fasdf2', 3, 2, false, false, 0, false),
  ('1fasdf2', 3, 3, false, false, 0, false);
