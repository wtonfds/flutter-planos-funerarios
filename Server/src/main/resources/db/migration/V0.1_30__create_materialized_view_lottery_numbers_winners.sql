drop materialized view if exists mview_lottery_numbers_winners;
alter table if exists frl_user_client drop column if exists luck_number;
alter table if exists frl_user_client add column luck_number varchar(10);
create materialized view mview_lottery_numbers_winners as
select uc.name as nome,
       uc.cpf as cpf,
       uc.luck_number as numero_da_sorte,
       vln.data_extracao as data_extracao
from frl_user_client uc join mview_lottery_numbers vln on (uc.luck_number = vln.numeros_gerados
                                                              and vln.aprovado_por is not null);
