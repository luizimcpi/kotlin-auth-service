CREATE TABLE contacts(
   id serial PRIMARY KEY,
   name VARCHAR (100) NOT NULL,
   email VARCHAR (100) NOT NULL,
   phone VARCHAR(20) NOT NULL,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL,
   user_id serial NOT NULL,
   CONSTRAINT fk_user
   FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE INDEX IF NOT EXISTS "contact_id_and_user_id_idx" ON contacts USING btree("id", "user_id");

CREATE INDEX IF NOT EXISTS "contact_name_idx" ON contacts USING btree("name");

CREATE INDEX IF NOT EXISTS "contact_email_idx" ON contacts USING btree("email");

CREATE INDEX IF NOT EXISTS "contact_phone_idx" ON contacts USING btree("phone");
