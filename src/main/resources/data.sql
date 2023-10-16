-- CREATE USER IF NOT EXISTS
INSERT
    IGNORE INTO users
SET
    id = 0,
    name = 'KIPRUTO BARNO',
    username = 'admin',
    password = '$2a$10$TGeL5S.J9lkwS6C9moZNXO4xKYNMzoIzFe4BJ.aG/u2vq5n5zTn0y';

-- CREATE ADMIN ROLE IF NOT EXISTS
INSERT
    IGNORE INTO roles
SET
    id = 0,
    name = 'ADMIN';

-- ADD ROLE TO USER
SELECT
    u.id,
    u.name,
    u.password,
    u.username
FROM
    users u
WHERE
    u.username = 'admin';

SELECT
    r0.user_id,
    r1.id,
    r1.name
FROM
    users_roles r0
    JOIN roles r1 ON r1.id = r0.role_id
WHERE
    r0.user_id = 0;

SELECT
    r0.id,
    r0.name
FROM
    roles r0
WHERE
    r0.name = 'ADMIN';

INSERT INTO
    users_roles (user_id, role_id)
VALUES
    (0, 0);
