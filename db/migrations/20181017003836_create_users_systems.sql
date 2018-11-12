-- migrate:up

CREATE TABLE users_systems(
	id	INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INTEGER,
  system_id INTEGER,
  CONSTRAINT fk_user_system_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_system_id FOREIGN KEY (system_id) REFERENCES systems(id)
);

-- migrate:down

ALTER TABLE users_systems DROP FOREIGN KEY fk_user_system_user_id; 
ALTER TABLE users_systems DROP FOREIGN KEY fk_user_system_id; 
DROP TABLE IF EXISTS users_systems;
