CREATE USER IF NOT EXISTS 'authuser' @'localhost' IDENTIFIED BY 'authuser123';

CREATE DATABASE IF NOT EXISTS role_based_authorization;

GRANT ALL PRIVILEGES ON role_based_authorization.* TO 'authuser' @'localhost' WITH GRANT OPTION;

FLUSH PRIVILEGES;
