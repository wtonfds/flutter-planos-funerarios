alter table frl_subscribed_plan add column payment_slip_date int4;
alter table frl_subscribed_plan add column payment_type varchar(255);
alter table frl_subscribed_plan add column waiting_for_last_payment_date boolean;
