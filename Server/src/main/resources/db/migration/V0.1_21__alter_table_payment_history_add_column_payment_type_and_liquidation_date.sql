alter table frl_payment_history add column payment_type int4;
alter table frl_payment_history add column liquidation_date timestamp;
alter table frl_payment_history drop column subscribed_plan_id;