-- migrate:up

INSERT INTO systems (name) VALUES
  ('CanchasProviders');

-- migrate:down

DELETE FROM systems;
