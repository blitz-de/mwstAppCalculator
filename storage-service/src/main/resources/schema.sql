DROP TABLE IF EXISTS product_duplica;

CREATE TABLE product_duplica (
    id BIGINT auto_increment NOT NULL PRIMARY KEY,
    description VARCHAR (40),
    location VARCHAR (40),
    name VARCHAR (40),
    price DOUBLE
);