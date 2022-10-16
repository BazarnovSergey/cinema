CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  username varchar NOT NULL,
  email varchar UNIQUE,
  password varchar,
  phone varchar UNIQUE
);