-- migrate:up

CREATE TABLE users_permissions (
	id	INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  permission_id INT NOT NULL,
  CONSTRAINT fk_user_permission_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_permission_id FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

-- migrate:down

ALTER TABLE users_permissions DROP FOREIGN KEY fk_user_permission_user_id; 
ALTER TABLE users_permissions DROP FOREIGN KEY fk_user_permission_id; 
DROP TABLE IF EXISTS users_permissions;
