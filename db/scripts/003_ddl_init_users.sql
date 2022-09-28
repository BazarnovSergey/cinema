CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  username varchar(255) NOT NULL,
  email varchar(255) UNIQUE,
  password varchar(255),
  phone varchar(255) UNIQUE
);