DROP DATABASE IF EXISTS DB_Numismaster;
CREATE DATABASE IF NOT EXISTS DB_Numismaster;
USE DB_Numismaster;

CREATE TABLE TB_Country (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    code CHAR(3) NOT NULL UNIQUE,
    CONSTRAINT country_id PRIMARY KEY (id)
);

CREATE TABLE TB_Material (
    id INT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL UNIQUE,
    CONSTRAINT material_id PRIMARY KEY (id)
);

CREATE TABLE TB_Edge (
    id INT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL UNIQUE,
    CONSTRAINT edge_id PRIMARY KEY (id)
);

CREATE TABLE TB_Shape (
    id INT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL UNIQUE,
    CONSTRAINT shape_id PRIMARY KEY (id)
);

CREATE TABLE TB_Coin (
	id INT AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL UNIQUE,
	denomination INT NOT NULL,
	weight DECIMAL(10, 2) NOT NULL,
	diameter DECIMAL(10, 2) NOT NULL,
	thickness DECIMAL(10, 2) NOT NULL,
    rarity SMALLINT NOT NULL,
	country_id INT NOT NULL,
	FOREIGN KEY (country_id) REFERENCES TB_Country (id),
	CONSTRAINT coin_id PRIMARY KEY (id)
);

CREATE TABLE TB_User (
	id INT AUTO_INCREMENT,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	gender SMALLINT NOT NULL,
	birth_date DATE NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	cpf CHAR(11) NOT NULL UNIQUE,
	username VARCHAR(16) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
    type SMALLINT NOT NULL DEFAULT 0,
    is_blocked TINYINT DEFAULT 1,
	profile_photo LONGBLOB,
	registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT user_id PRIMARY KEY (id)
);

CREATE TABLE TB_Sale (
    id INT AUTO_INCREMENT,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	price DECIMAL(10,2) NOT NULL,
    buyer_id INT NOT NULL,
    seller_id INT NOT NULL,
    FOREIGN KEY (buyer_id) REFERENCES TB_User(id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES TB_User(id) ON DELETE CASCADE,
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

CREATE TABLE TB_Coin_Shape (
    id INT AUTO_INCREMENT,
    coin_id INT NOT NULL,
    shape_id INT NOT NULL,
    FOREIGN KEY (coin_id) REFERENCES TB_Coin(id),
    FOREIGN KEY (shape_id) REFERENCES TB_Shape(id),
    CONSTRAINT coin_shape_id PRIMARY KEY (id)
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
	coin_condition SMALLINT NOT NULL,
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
	FOREIGN KEY (coin_user_id) REFERENCES TB_Coin_User (id) ON DELETE CASCADE,
	FOREIGN KEY (sale_id) REFERENCES TB_Sale (id) ON DELETE CASCADE,
    CONSTRAINT coin_user_sale_id PRIMARY KEY (id)
);

CREATE TABLE TB_Request(
	id INT AUTO_INCREMENT,
    requested_item SMALLINT NOT NULL,
    notes TEXT NOT NULL,
    request_situation SMALLINT NOT NULL,
    request_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    close_request_date TIMESTAMP,
    CONSTRAINT request_situation_close_date CHECK (request_situation = 0 OR (request_situation > 0 AND close_request_date IS NOT NULL)),
    CONSTRAINT request_id PRIMARY KEY (id)
);

CREATE TABLE TB_User_Request(
	id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    request_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES TB_User (id),
    FOREIGN KEY (request_id) REFERENCES TB_Request (id) ON DELETE CASCADE,
    CONSTRAINT user_request_id PRIMARY KEY (id)
);

DELIMITER //
CREATE PROCEDURE DeleteUserRecords(IN userId INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;

    START TRANSACTION;
    
    DELETE FROM TB_User_Request WHERE user_id = userId;
    DELETE FROM TB_Request WHERE id IN (SELECT request_id FROM TB_User_Request WHERE user_id = userId);
    DELETE FROM TB_Coin_User_Sale WHERE coin_user_id IN (SELECT id FROM TB_Coin_User WHERE user_id = userId);
    DELETE FROM TB_Coin_User WHERE user_id = userId;
    DELETE FROM TB_Sale WHERE buyer_id = userId OR seller_id = userId;
    DELETE FROM TB_User WHERE id = userId;
    
    COMMIT;
END //
DELIMITER ;