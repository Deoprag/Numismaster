DROP DATABASE DB_Numismaster;
CREATE DATABASE IF NOT EXISTS DB_Numismaster;
USE DB_Numismaster;

CREATE TABLE TB_Country (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    code CHAR(2) NOT NULL UNIQUE,
    CONSTRAINT country_id PRIMARY KEY (id)
);

CREATE TABLE TB_Material (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT material_id PRIMARY KEY (id)
);

CREATE TABLE TB_Edge (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT edge_id PRIMARY KEY (id)
);

CREATE TABLE TB_Shape (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT shape_id PRIMARY KEY (id)
);

CREATE TABLE TB_Coin (
	id INT AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL UNIQUE,
	denomination DECIMAL(10, 2) NOT NULL,
	weight DECIMAL(10, 2) NOT NULL,
	diameter DECIMAL(10, 2) NOT NULL,
	thickness DECIMAL(10, 2) NOT NULL,
    rarity VARCHAR(20) NOT NULL,
	country_id INT NOT NULL,
	shape_id INT NOT NULL,
	FOREIGN KEY (country_id) REFERENCES TB_Country (id),
	FOREIGN KEY (shape_id) REFERENCES TB_Shape (id),
	CONSTRAINT coin_id PRIMARY KEY (id)
);

CREATE TABLE TB_User (
	id INT AUTO_INCREMENT,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	gender CHAR NOT NULL,
	birth_date DATE NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	cpf CHAR(11) NOT NULL UNIQUE,
	username VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
    type VARCHAR(7) DEFAULT "DEFAULT",
    is_blocked TINYINT DEFAULT 1,
	profile_photo LONGBLOB,
	registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT user_id PRIMARY KEY (id)
);

CREATE TABLE TB_Sale (
    id INT AUTO_INCREMENT,
    total_price DECIMAL(10,2) NOT NULL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    buyer_id INT NOT NULL,
    seller_id INT NOT NULL,
    FOREIGN KEY (buyer_id) REFERENCES TB_User(id),
    FOREIGN KEY (seller_id) REFERENCES TB_User(id),
    CONSTRAINT sale_id PRIMARY KEY (id)
);

CREATE TABLE TB_Coin_Material (
    id INT AUTO_INCREMENT,
    coin_id INT NOT NULL,
    material_id INT NOT NULL,
    FOREIGN KEY (coin_id) REFERENCES TB_Coin(id),
    FOREIGN KEY (material_id) REFERENCES TB_Material(id),
    CONSTRAINT coin_material_id PRIMARY KEY (id)
);

CREATE TABLE TB_Coin_Edge (
    id INT AUTO_INCREMENT,
    coin_id INT NOT NULL,
    edge_id INT NOT NULL,
    FOREIGN KEY (coin_id) REFERENCES TB_Coin(id),
    FOREIGN KEY (edge_id) REFERENCES TB_Edge(id),
    CONSTRAINT coin_edge_id PRIMARY KEY (id)
);

CREATE TABLE TB_Coin_User (
	id INT AUTO_INCREMENT,
	user_id INT NOT NULL,
	coin_id INT NOT NULL,
	year SMALLINT NOT NULL,
	coin_condition VARCHAR(5) NOT NULL,
	is_for_sale TINYINT NOT NULL,
	price DECIMAL(10, 2),
	image_front LONGBLOB,
	image_back LONGBLOB,
	notes TEXT,
	CONSTRAINT price_if_for_sale CHECK (is_for_sale = 0 OR (is_for_sale = 1 AND price IS NOT NULL)),
	FOREIGN KEY (user_id) REFERENCES TB_User (id),
	FOREIGN KEY (coin_id) REFERENCES TB_Coin (id),
	CONSTRAINT coin_user_id PRIMARY KEY (id)
);

CREATE TABLE TB_Coin_User_Sale (
	id INT AUTO_INCREMENT,
	coin_user_id INT NOT NULL,
	sale_id INT NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	quantity INT NOT NULL,
	FOREIGN KEY (coin_user_id) REFERENCES TB_Coin_User (id),
	FOREIGN KEY (sale_id) REFERENCES TB_Sale (id),
    CONSTRAINT coin_sale_id PRIMARY KEY (id)
);

use db_numismaster;
select * from tb_user;