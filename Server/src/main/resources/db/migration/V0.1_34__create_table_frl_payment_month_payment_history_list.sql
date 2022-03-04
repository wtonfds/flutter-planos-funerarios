drop table if exists frl_payment_month_payment_history_list;

create table frl_payment_history_payment_month(
    payment_month_id            int8    not null,
    payment_history_list_id     int8    not null
);

alter table frl_payment_history drop column payment_month_id;