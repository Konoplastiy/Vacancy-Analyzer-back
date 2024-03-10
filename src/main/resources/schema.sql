CREATE TABLE IF NOT EXISTS `vacancy`
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR(255) NOT NULL,
    cityName         VARCHAR(255) NOT NULL,
    date             TIMESTAMP    NOT NULL,
    companyName      VARCHAR(255) NOT NULL,
    shortDescription VARCHAR(255) NOT NULL
);