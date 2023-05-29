use db_numismaster;
select * from tb_user;
select * from tb_coin;
select * from tb_coin_user;

select ur.id, r.id, u.id, u.first_name from tb_user_request ur
inner join tb_request r on r.id = ur.request_id
inner join tb_user u on u.id = ur.user_id;


SELECT * FROM tb_coin_user c WHERE c.is_for_sale = 1;

update tb_user set type = 1 where id = 2;
update tb_coin set rarity = 1 where rarity = 1;

delete from tb_user where id = 4;

select c.name, c.denomination, c.weight, c.diameter, c.thickness, c.rarity, cu.year, cu.coin_condition, cu.is_for_sale, cu.price, u.first_name as dono
FROM tb_coin_user cu
INNER JOIN tb_coin c ON cu.coin_id = c.id
INNER JOIN tb_user u ON cu.user_id = u.id;

SELECT *
FROM tb_coin_user
WHERE user_id = 1
ORDER BY raridade DESC, condicao DESC, ano DESC
LIMIT 1;
