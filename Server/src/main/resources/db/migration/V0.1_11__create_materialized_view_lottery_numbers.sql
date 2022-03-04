create materialized view mview_lottery_numbers as
select l.original_number1                        as numero_1,
       l.original_number2                        as numero_2,
       l.original_number3                        as numero_3,
       l.original_number4                        as numero_4,
       l.original_number5                        as numero_5,
       to_char(l.draw_day :: DATE, 'dd/mm/yyyy') AS data_extracao,
       l.generated_numbers                       as numeros_gerados,
       f.name                                    as aprovado_por
from frl_lottery_number as l
         left join frl_user_farol as f on f.id = l.approved_by_id