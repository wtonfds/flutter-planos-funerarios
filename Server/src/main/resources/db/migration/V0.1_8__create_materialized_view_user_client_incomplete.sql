CREATE MATERIALIZED VIEW mview_incomplete_user_client AS
SELECT c.id,
       email,
       name                                     as nome,
       c.active                                 as ativo,
       cpf,
       to_char(birth_day :: DATE, 'dd/mm/yyyy') as aniversario,
       rg,
       telephone                                as telefone,
       client_type                              as tipo_do_cliente
from frl_user_client c
         left join frl_subscribed_plan sp on c.id = sp.beneficiary_id
where sp.active is null;
