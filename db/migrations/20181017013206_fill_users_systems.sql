-- migrate:up

INSERT INTO users_systems (user_id, system_id) VALUES
  (1, 1), (2, 1);

-- migrate:down

DELETE FROM users_systems;
