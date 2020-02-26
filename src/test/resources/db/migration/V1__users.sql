CREATE TABLE users(
  id bigint primary key,
  name text,
  surname text,
  age int,
  active boolean,
  birthday timestamp with time zone
);