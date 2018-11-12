-- migrate:up

CREATE TABLE user_logs (
	id	INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	moment	DATETIME NOT NULL,
  user_id INT NOT NULL,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

-- migrate:down

ALTER TABLE user_logs DROP FOREIGN KEY fk_user_id; 
DROP TABLE IF EXISTS user_logs;
