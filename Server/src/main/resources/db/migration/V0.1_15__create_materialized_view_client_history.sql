create materialized view mview_client_history as
select l.description as descricao,
       l.type        as tipo,
       to_char(l.date :: DATE, 'dd/mm/yyyy hh:mm') AS horario
from frl_log_client_history l;