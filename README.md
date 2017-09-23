WEB application "Users base"

Created for Exorigo-Upos sp. z o.o.

Pylypchenko Vadym 23.09.2017

==========================================================================

-- SQl query for create DB and tables --

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

