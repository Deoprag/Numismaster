USE db_numismaster;

INSERT INTO TB_Country (name, code) VALUES
('Afeganistao', 'AF'),
('Ilhas Aland', 'AX'),
('Brasil', 'BR'),
('Estados Unidos da America', 'EUA'),
('Nova Zelandia', 'NZ');

INSERT INTO TB_User (username, first_name, last_name, gender, password, birth_date, email, cpf)
VALUES
('johndoe', 'John', 'Doe', 0,'pa$$w0rd1',  '1985-01-01', 'johndoe@email.com', '12345678901'),
('janedoe', 'Jane', 'Doe', 1,'pa$$w0rd2',  '1987-02-02', 'janedoe@email.com', '12345678902'),
('jimsmith', 'Jim', 'Smith', 0,'pa$$w0rd3',  '1989-03-03', 'jimsmith@email.com', '12345678903'),
('sarahjones', 'Sarah', 'Jones', 1, 'pa$$w0rd4',  '1991-04-04', 'sarahjones@email.com', '12345678904'),
('mikebrown', 'Mike', 'Brown', 0,'pa$$w0rd5', '1993-05-05', 'mikebrown@email.com', '12345678905'),
('jenniferwhite', 'Jennifer', 'White', 1, 'pa$$w0rd6', '1995-06-06', 'jenniferwhite@email.com', '12345678906'),
('davidsmith', 'David', 'Smith', 0, 'pa$$w0rd7', '1997-07-07', 'davidsmith@email.com', '12345678907'),
('emmamiller', 'Emma', 'Miller', 1, 'pa$$w0rd8', '1999-08-08', 'emmamiller@email.com', '12345678908'),
('williamjones', 'William', 'Jones', 0, 'pa$$w0rd9', '2001-09-09', 'williamjones@email.com', '12345678909'),
('oliviagreen', 'Olivia', 'Green', 1, 'pa$$w0rd10', '2003-10-10', 'oliviagreen@email.com', '12345678910');

INSERT INTO TB_Shape (name) 
VALUES 
("Circular"), 
("Quadrado"), 
("Esfera"), 
("Triangular");

INSERT INTO TB_Material (name)
VALUES
("Ferro"),
("Chumbo"),
("Estanho"),
("Cobre"),
("Ouro"),
("Prata"),
("Madeira"),
("Platina"),
("Bronze"),
("Aço");

INSERT INTO TB_Edge (name)
VALUES
("Lisa"),
("Serrilhada"),
("Plana"),
("Inscrita"),
("Dentada");

INSERT INTO TB_Coin (name, country_id, denomination, weight, diameter, thickness, rarity)
VALUES
('1 Real (XXXI Olimpíadas de Verão de 2016 no Rio - Rugby)', 32, 1.00, 7, 27, 1.95, 1),
('1 Real (XXXI Olimpíadas de Verão de 2016 no Rio - Futebol)', 32, 1.00, 7, 27, 1.95, 1),
('1 Real (XXXI Olimpíadas de Verão de 2016 no Rio - Basquete)', 32, 1.00, 7, 27, 1.95, 1),
('1 Real (XXXI Olimpíadas de Verão de 2016 no Rio - Paracanoagem)', 32, 1.00, 7, 27, 1.95, 1);

INSERT INTO TB_Coin_Material (coin_id, material_id)
VALUES
(1, 1),
(1, 4),
(2, 1),
(2, 4),
(3, 1),
(3, 4),
(4, 1),
(4, 4);

INSERT INTO TB_Coin_Shape (coin_id, shape_id)
VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1);

INSERT INTO TB_Coin_Edge (coin_id, edge_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(4, 1),
(4, 2);

INSERT INTO TB_Coin_User (user_id, coin_id, year, coin_condition, is_for_sale, price)
VALUES
(1, 1, 2016, 0, 1, 49.90),
(1, 2, 2016, 1, 0, 0),
(1, 3, 2016, 0, 1, 54.90),
(1, 4, 2016, 1, 0, 0);