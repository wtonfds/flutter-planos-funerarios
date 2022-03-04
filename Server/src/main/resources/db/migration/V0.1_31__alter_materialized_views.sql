drop materialized view if exists mview_user_client;

CREATE MATERIALIZED VIEW mview_user_client AS
SELECT c.id                                              AS id,
       c.name                                            AS nome,
       c.cpf,
       c.rg,
       c.email,
       to_char(c.birth_day :: DATE, 'dd/mm/yyyy')        AS aniversario,
       c.telephone                                       AS telefone,
       c.active                                          AS ativo,
       c.client_type                                     AS tipo_do_cliente,
       to_char(c.created_at :: DATE, 'dd/mm/yyyy hh:mm') AS criado_em,
       h.name                                            AS nome_do_titular,
       h.cpf                                             AS cpf_do_titular,
       h.email                                           AS email_do_titular,
       to_char(h.birth_day :: DATE, 'dd/mm/yyyy')        AS aniversario_do_titular,
       h.telephone                                       AS telefone_do_titular,
       h.active                                          AS titular_ativo,
       ad.country                                        AS pais,
       ad.province                                       AS estado,
       ad.zip_code                                       AS cep,
       ad.city                                           AS cidade,
       ad.neighborhood                                   as bairro,
       ad.street                                         as rua,
       ad.complement                                     as complemento,
       ad.number                                         as numero

FROM frl_user_client AS c
         LEFT JOIN frl_user_client AS h ON c.holder_id = h.id
         LEFT JOIN frl_subscribed_plan AS sp ON (sp.beneficiary_id = c.id and sp.active = true)
         LEFT JOIN frl_address AS ad ON sp.address_id = ad.id;


drop materialized view if exists mview_new_user_client;
-- This materialized view should pick only the newer registered clients in a 30 days range
CREATE MATERIALIZED VIEW mview_new_user_client AS
SELECT c.id                                              AS id,
       c.name                                            AS nome,
       c.cpf,
       c.rg,
       c.email,
       to_char(c.birth_day :: DATE, 'dd/mm/yyyy')        AS aniversario,
       c.telephone                                       AS telefone,
       c.active                                          AS ativo,
       c.client_type                                     AS tipo_do_cliente,
       to_char(c.created_at :: DATE, 'dd/mm/yyyy hh:mm') AS criado_em,
       h.name                                            AS nome_do_titular,
       h.cpf                                             AS cpf_do_titular,
       h.email                                           AS email_do_titular,
       to_char(h.birth_day :: DATE, 'dd/mm/yyyy')        AS aniversario_do_titular,
       h.telephone                                       AS telefone_do_titular,
       h.active                                          AS titular_ativo,
       ad.country                                        AS pais,
       ad.province                                       AS estado,
       ad.zip_code                                       AS cep,
       ad.city                                           AS cidade,
       ad.neighborhood                                   as bairro,
       ad.street                                         as rua,
       ad.complement                                     as complemento,
       ad.number                                         as numero

FROM frl_user_client AS c
         LEFT JOIN frl_user_client AS h ON c.holder_id = h.id
         LEFT JOIN frl_subscribed_plan AS sp ON (sp.beneficiary_id = c.id and sp.active = true)
         LEFT JOIN frl_address AS ad ON sp.address_id = ad.id
WHERE c.created_at > NOW() - INTERVAL '30 days';


drop materialized view if exists mview_incomplete_user_client;

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
where sp.active is null and c.holder_id is null;

alter table if exists frl_log_client_history drop column if exists client_cpf;
alter table if exists frl_log_client_history drop column if exists client_name;

alter table if exists frl_log_client_history add column client_cpf varchar(20) not null default '0';
alter table if exists frl_log_client_history add column client_name varchar(255) not null default '0';

drop materialized view if exists mview_client_history;

create materialized view mview_client_history as
select l.client_cpf     as cpf,
       l.client_name    as nome,
       l.description    as descricao,
       l.type           as tipo,
       to_char(l.date :: DATE, 'dd/mm/yyyy hh:mm') AS horario
from frl_log_client_history l;


drop materialized view if exists mview_user_client_is_default;

CREATE MATERIALIZED VIEW mview_user_client_is_default AS
SELECT c.id                                              AS id,
       c.name                                            AS nome,
       sp.is_default                                     as inadimplente,
       c.cpf,
       c.rg,
       c.email,
       to_char(c.birth_day :: DATE, 'dd/mm/yyyy')        AS aniversario,
       c.telephone                                       AS telefone,
       c.active                                          AS ativo,
       c.client_type                                     AS tipo_do_cliente,
       to_char(c.created_at :: DATE, 'dd/mm/yyyy hh:mm') AS criado_em,
       h.name                                            AS nome_do_titular,
       h.cpf                                             AS cpf_do_titular,
       h.email                                           AS email_do_titular,
       to_char(h.birth_day :: DATE, 'dd/mm/yyyy')        AS aniversario_do_titular,
       h.telephone                                       AS telefone_do_titular,
       h.active                                          AS titular_ativo,
       ad.country                                        AS pais,
       ad.province                                       AS estado,
       ad.zip_code                                       AS cep,
       ad.city                                           AS cidade,
       ad.neighborhood                                   as bairro,
       ad.street                                         as rua,
       ad.complement                                     as complemento,
       ad.number                                         as numero,
       count(fpm)                           as parcelas_atrasadas
FROM frl_user_client AS c
       LEFT JOIN frl_user_client AS h ON c.holder_id = h.id
       LEFT JOIN frl_subscribed_plan AS sp ON sp.beneficiary_id = c.id
       LEFT JOIN frl_address AS ad ON sp.address_id = ad.id
       LEFT JOIN frl_payment_month fpm on sp.id = fpm.subscribed_plan_id and fpm.paid = false
WHERE sp.is_default = true
group by c.id, sp.is_default, h.name, c.name, sp.is_default, c.cpf, c.rg, c.email, to_char(c.birth_day :: DATE, 'dd/mm/yyyy'), c.telephone, c.active, c.client_type, to_char(c.created_at :: DATE, 'dd/mm/yyyy hh:mm'), c.id, h.cpf, h.email, to_char(h.birth_day :: DATE, 'dd/mm/yyyy'), h.telephone, h.active, ad.country, ad.province, ad.zip_code, ad.city, ad.neighborhood, ad.street, ad.complement, ad.number;
