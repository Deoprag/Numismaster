use db_numismaster;
select * from tb_coin_user;
select cus.id as "ID da transação", s.sale_date as "Data da venda", b.first_name as "Comprador", v.first_name as "Vendedor", s.price as "Preço" from tb_coin_user_sale cus
inner join tb_sale s on s.id = cus.sale_id
inner join tb_coin_user cu on cu.id = cus.coin_user_id
inner join tb_user b on b.id = s.buyer_id
inner join tb_user v on v.id = s.seller_id;

update tb_user set type = 1 where id = 2;

update tb_user set birth_date = '2004-02-27' where id = 1;
update tb_coin set rarity = 1 where rarity = 1;

update tb_request set close_request_date = CURRENT_TIMESTAMP, request_situation = 1 where id = 1;

delete from tb_user where id = 4;

alter table tb_request 
add column request_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
add column close_request_date TIMESTAMP,
add CONSTRAINT request_situation_close_date CHECK (request_situation = 0 OR (request_situation > 0 AND close_request_date IS NOT NULL));

select * from tb_user_request ur
inner join tb_request r on ur.id = r.id;

select c.name, c.denomination, c.weight, c.diameter, c.thickness, c.rarity, cu.year, cu.coin_condition, cu.is_for_sale, cu.price, u.first_name as dono
FROM tb_coin_user cu
INNER JOIN tb_coin c ON cu.coin_id = c.id
INNER JOIN tb_user u ON cu.user_id = u.id;