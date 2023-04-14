use db_numismaster;
select * from tb_user;

update tb_user set is_blocked = 1 where id = 1;
update tb_coin set rarity = 1 where rarity = 2;

select c.name, c.denomination, c.weight, c.diameter, c.thickness, c.rarity, cu.year, cu.coin_condition, cu.is_for_sale, cu.price, u.first_name as dono
FROM tb_coin_user cu
INNER JOIN tb_coin c ON cu.coin_id = c.id
INNER JOIN tb_user u ON cu.user_id = u.id;
