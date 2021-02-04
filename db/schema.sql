CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE candidate (
   id SERIAL PRIMARY KEY,
   name TEXT,
   photoId int references photo(id) not null,
   city_id int references cities(id)
);

create table photo (
    id serial primary key
);

create table users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT UNIQUE,
    password TEXT
);

create table cities (
    id serial primary key,
    name text unique not null
);