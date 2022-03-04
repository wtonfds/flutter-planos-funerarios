CREATE MATERIALIZED VIEW mview_accredited AS
SELECT ac.id as id_credenciado,
       name as nome,
       cnpj,
       coupon_origin as origem_cupom,
       code as codigo,
       active as ativo,
       category as categoria,
       subcategory as subcategoria,
       telephone as telefone,
       email,
       zip_code as cep,
       street as rua,
       number as numero,
       complement as complemento,
       neighborhood as bairro,
       city as cidade,
       province as estado,
       country as pais
from frl_accredited as ac
    left join frl_address ad on ac.address_id = ad.id