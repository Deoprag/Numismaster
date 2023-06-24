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
('1 Dolar (Apollo 11 - 50º Aniversario)', 4, 1.00, 26.7, 38.1, 6.17, 2),
('1 Dolar (Inovacao Americana - Dellaware)', 4, 1.00, 8.1, 26.4, 2, 2),
('2 Dólares (Símbolos do Estado - Nova York)', 4, 2, 8.36, 28.40, 2.00, 3),
('5 Centavos (Kiwi - Aves da Nova Zelândia)', 5, 0.05, 2.80, 23.00, 1.65, 0),
('10 Centavos (Fern - Plantas da Nova Zelândia)', 5, 0.10, 3.30, 20.50, 1.40, 1),
('20 Centavos (Tui - Aves da Nova Zelândia)', 5, 0.20, 4.40, 21.75, 1.58, 2),
('50 Centavos (Kowhai - Plantas da Nova Zelândia)', 5, 0.50, 5.00, 24.75, 1.94, 3),
('1 Dólar (Kiwi - Aves da Nova Zelândia)', 5, 1, 8.00, 26.00, 2.00, 4),
('1 Centavo (D. Maria I - Descobrimentos Portugueses)', 6, 0.01, 2.30, 16.00, 1.45, 1),
('2 Centavos (Marquês de Pombal - Descobrimentos Portugueses)', 6, 0.02, 3.06, 18.85, 1.67, 2),
('5 Centavos (Navegações e Descobrimentos Portugueses)', 6, 0.05, 4.10, 20.00, 1.90, 3),
('10 Centavos (Agricultura - Descobrimentos Portugueses)', 6, 0.10, 4.60, 20.85, 1.90, 4),
('20 Centavos (Industrialização - Descobrimentos Portugueses)', 6, 0.20, 5.30, 22.20, 1.90, 5),
('50 Centavos (Mares da Soberania - Descobrimentos Portugueses)', 6, 0.50, 7.60, 24.25, 2.20, 6),
('1 Pound (União Europeia - Símbolos do Reino Unido)', 8, 1.00, 9.50, 22.50, 3.15, 1),
('1 Penny (Cabeça da Rainha - Símbolos do Reino Unido)', 8, 0.01, 3.56, 20.32, 1.65, 0),
('2 Pence (Flor de Cardo - Símbolos do Reino Unido)', 8, 0.02, 7.12, 25.91, 2.03, 1),
('5 Pence (Coroa de Carvalho - Símbolos do Reino Unido)', 8, 0.05, 3.25, 18.00, 1.70, 2),
('10 Pence (Leão - Símbolos do Reino Unido)', 8, 0.10, 6.50, 24.50, 1.85, 3),
('20 Pence (Tartaruga - Símbolos do Reino Unido)', 8, 0.20, 5.00, 21.40, 1.70, 4),
('50 Pence (Escudo Real - Símbolos do Reino Unido)', 8, 0.50, 8.00, 27.30, 1.78, 5),
('1 Penny (Leão - Símbolos do Reino Unido)', 8, 0.01, 3.56, 20.32, 1.65, 0);

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

INSERT INTO TB_Sale(price, buyer_id, seller_id)
VALUES
(149.9, 1, 2);

INSERT INTO TB_Coin_User_Sale(coin_user_id, sale_id)
VALUES
(1, 1);