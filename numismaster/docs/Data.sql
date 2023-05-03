USE db_numismaster;

INSERT INTO TB_Country (name, code) VALUES
('Afeganistao', 'AF'),
('Ilhas Aland', 'AX'),
('Brasil', 'BR'),
('Estados Unidos da America', 'EUA'),
('Nova Zelandia', 'NZ');

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
("Aço"),
("Manganes");

INSERT INTO TB_Edge (name)
VALUES
("Lisa"),
("Serrilhada"),
("Plana"),
("Inscrita"),
("Dentada");

INSERT INTO TB_Coin (name, country_id, denomination, weight, diameter, thickness, rarity)
VALUES
('1 Real (XXXI Olimpiadas de Verao de 2016 no Rio - Rugby)', 3, 1.00, 7, 27, 1.95, 1),
('1 Real (XXXI Olimpiadas de Verao de 2016 no Rio - Futebol)', 3, 1.00, 7, 27, 1.95, 1),
('1 Real (XXXI Olimpiadas de Verao de 2016 no Rio - Basquete)', 3, 1.00, 7, 27, 1.95, 1),
('1 Real (XXXI Olimpiadas de Verao de 2016 no Rio - Paracanoagem)', 3, 1.00, 7, 27, 1.95, 1),
('1 Dolar (Apollo 11 - 50º Aniversario', 4, 1.00, 26.7, 38.1, 6.17, 2),
('1 Dolar (Inovacao Americana - Dellaware)', 4, 1.00, 8.1, 26.4, 2, 2);

INSERT INTO TB_Coin_Material (coin_id, material_id)
VALUES
(1, 1),
(1, 4),
(2, 1),
(2, 4),
(3, 1),
(3, 4),
(4, 1),
(4, 4),
(5, 10),
(6, 10);

INSERT INTO TB_Coin_Shape (coin_id, shape_id)
VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1);

INSERT INTO TB_Coin_Edge (coin_id, edge_id)
VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(4, 1),
(4, 2),
(5, 3),
(6, 3);

INSERT INTO TB_Coin_User (user_id, coin_id, year, coin_condition, is_for_sale, price)
VALUES
(1, 1, 2016, 0, 1, 49.90),
(1, 2, 2016, 1, 0, 0),
(1, 3, 2016, 0, 1, 54.90),
(1, 4, 2016, 1, 0, 0);