CREATE TABLE if not exists sessions (
   id SERIAL PRIMARY KEY,
   name TEXT,
   poster bytea
);