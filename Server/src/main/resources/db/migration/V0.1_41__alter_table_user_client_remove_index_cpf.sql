-- in this new flow a dependent will not have cpf until a holder finishes the plan purchase
-- so the cpf will have to accept null values in this case
alter table frl_user_client alter column cpf drop not null;
