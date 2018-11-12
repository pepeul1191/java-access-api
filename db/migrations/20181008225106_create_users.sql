-- migrate:up

CREATE TABLE users (
	id	INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user	VARCHAR(25) NOT NULL,
  pass	VARCHAR(80) NOT NULL,
  email	VARCHAR(40) NOT NULL,
  user_state_id INT NOT NULL,
  CONSTRAINT fk_user_state FOREIGN KEY (user_state_id) REFERENCES user_states(id)
);

-- migrate:down

ALTER TABLE users DROP FOREIGN KEY fk_user_state; 
DROP TABLE IF EXISTS users;
