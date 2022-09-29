ALTER TABLE ticket ADD CONSTRAINT ticket_unique UNIQUE (session_id, pos_row, cell);
