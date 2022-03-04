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
         LEFT JOIN frl_subscribed_plan AS sp ON sp.beneficiary_id = c.id
         LEFT JOIN frl_address AS ad ON sp.address_id = ad.id;