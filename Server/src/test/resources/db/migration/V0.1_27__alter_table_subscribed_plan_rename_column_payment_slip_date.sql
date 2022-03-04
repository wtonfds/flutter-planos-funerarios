-- The syntax for renaming columns in H2 is different
alter table frl_subscribed_plan alter column payment_slip_date rename to payment_day;
