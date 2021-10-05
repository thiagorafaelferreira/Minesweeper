DROP TABLE IF EXISTS mine_field;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  username VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  birth_date DATE NOT NULL,
  password VARCHAR(250) NOT NULL
);

INSERT INTO users (name, username, email, birth_date, password) VALUES
  ('Thiago', 'thiagueira', 'thiago@gmail.com', '1987-01-28', '09876'),
  ('Gabriela', 'gabriela', 'gabriela@gmail.com', '1988-02-10', '01234');
