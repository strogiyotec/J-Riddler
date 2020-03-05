ALTER TABLE users
  ADD CONSTRAINT age_positive CHECK (age > 0);

ALTER TABLE users
    ADD CONSTRAINT name_length CHECK (char_length(name) > 5);

ALTER TABLE users
  ADD CONSTRAINT birthday_value CHECK (birthday > '1997-01-01');