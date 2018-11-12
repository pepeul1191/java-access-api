-- migrate:up

INSERT INTO systems (name) VALUES
  ('FreelancersWeb');

-- migrate:down

DELETE FROM systems;
