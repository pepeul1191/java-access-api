-- migrate:up

CREATE TABLE users_keys (
	id	INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  activation VARCHAR(40),
  reset VARCHAR(40),
  user_id INT NOT NULL,
  CONSTRAINT fk_user_key_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- migrate:down

ALTER TABLE users_keys DROP FOREIGN KEY fk_user_key_id; 
DROP TABLE IF EXISTS users_keys;
