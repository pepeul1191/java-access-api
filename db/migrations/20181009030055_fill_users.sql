-- migrate:up

INSERT INTO users (user, pass, email, user_state_id) VALUES
  ('pepe', 'YcF54fUd4tXyz4oLLzeIvw==', 'jvaldivia@softweb.pe', 1),
  ('yacky', '67aePVMfgorpHXCvcqEsOg==', 'yramiez@softweb.pe', 2);

-- migrate:down

DELETE FROM users;
