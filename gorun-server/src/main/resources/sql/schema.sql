CREATE TABLE snippets
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    version       INT          NOT NULL,
    display_order INT DEFAULT 0,
    title         VARCHAR(32)  NOT NULL,
    brief         VARCHAR(128) NOT NULL,
    explanation   MEDIUMTEXT   NOT NULL,
    code          MEDIUMTEXT   NOT NULL
)