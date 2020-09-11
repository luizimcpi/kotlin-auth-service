CREATE TABLE users(
   id serial PRIMARY KEY,
   name VARCHAR (100) NOT NULL,
   email VARCHAR (100) NOT NULL,
   password VARCHAR (150) NOT NULL,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
);