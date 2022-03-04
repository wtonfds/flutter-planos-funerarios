drop sequence if exists frl_payment_month_sequence;
create sequence frl_payment_month_sequence start 1 increment 1;

create table frl_payment_month
(
    id                 int8    not null,
    month              int4    not null,
    year               int4    not null,
    subscribed_plan_id int8    not null,
    paid               boolean not null default false,
    primary key (id)
);

alter table frl_payment_month add constraint FK_payment_month_subscribed_plan foreign key (subscribed_plan_id) references frl_subscribed_plan;

alter table frl_payment_history add column payment_month_id int8 not null;
alter table frl_payment_history add constraint FK_payment_history_payment_month foreign key (payment_month_id) references frl_payment_month;
