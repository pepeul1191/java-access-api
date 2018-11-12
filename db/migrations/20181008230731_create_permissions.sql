-- migrate:up

CREATE TABLE permissions (
	id	INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name	VARCHAR(40) NOT NULL
);

-- migrate:down

DROP TABLE IF EXISTS permissions;
