CREATE TABLE IF NOT EXISTS `source`
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    link VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `vacancy`
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR(255) NOT NULL,
    cityName         VARCHAR(255) NOT NULL,
    date             TIMESTAMP    NOT NULL,
    companyName      VARCHAR(255) NOT NULL,
    shortDescription VARCHAR(1000) NOT NULL,
    source_id        INT,
    FOREIGN KEY (source_id) REFERENCES `source`(id)
);