-- migrate:up

CREATE TABLE correlations (
	id	INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);
ALTER TABLE correlations AUTO_INCREMENT = 20180000;

-- migrate:down

ALTER TABLE correlations AUTO_INCREMENT = 1;
DROP TABLE IF EXISTS correlations;
