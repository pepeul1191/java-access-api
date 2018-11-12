-- migrate:up

INSERT INTO user_states (name) VALUES 
  ('active'),
  ('inactive'),
  ('suspended'),
  ('deleted'),
  ('vacationing'),
  ('email_pending'),
  ('incompleted');

-- migrate:down

TRUNCATE user_states;
