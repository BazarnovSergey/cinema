CREATE TABLE if not exists ticket (
   id SERIAL PRIMARY KEY,
   session_id INT NOT NULL REFERENCES sessions(id),
   pos_row INT NOT NULL,
   cell INT NOT NULL,
   user_id INT NOT NULL REFERENCES users(id)
);