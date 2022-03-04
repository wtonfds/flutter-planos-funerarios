alter table frl_general
    add column plan_benefits_details varchar(511);

update frl_general
set plan_benefits_details =
'No Vida Plano, os benefícios inclusos são:
Sorteio semanal de R$ 20.000,00
Rede de ofertas em todo o Brasil
Assistência funeral 24 horas
Seguro de vida de até R$ 10.000,00
Consultas e exames com descontos';
