DROP DATABASE IF EXISTS usersdb;
CREATE DATABASE IF NOT EXISTS usersdb
  DEFAULT CHARACTER SET utf8;
USE usersdb;

-- users
CREATE TABLE IF NOT EXISTS users (
  id       INT UNSIGNED           NOT NULL  AUTO_INCREMENT,
  name VARCHAR(300)           NOT NULL  DEFAULT '',
  surname VARCHAR(300)           NOT NULL  DEFAULT '',
  password VARCHAR(300)           NOT NULL  DEFAULT '',
  gender   ENUM ('MALE', 'FEMALE') NOT NULL,
  email    VARCHAR(300)           DEFAULT '',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;

-- populate DB

INSERT INTO users VALUES (1,'Vadym','Pylypchenko','11111','MALE','vadym@ukr.net');
INSERT INTO users VALUES (2,'Tanya','Kovalenko','22222','FEMALE','tanya@ukr.net');
INSERT INTO users VALUES (3,'Vasya','Ivasik','33333','MALE','vasya@ukr.net');
