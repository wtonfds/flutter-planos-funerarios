drop sequence if exists frl_payment_history_sequence;
create sequence frl_payment_history_sequence start 1 increment 1;

drop table if exists frl_payment_history;

create table frl_payment_history (
    id                  int8            not null,
    created_at          timestamp       not null,
    order_id            varchar(255)    not null,
    payment_id          varchar(255)    not null,
    payment_slip_id     varchar(255),
    value               float           not null,
    status              varchar(255)    not null,
    subscribed_plan_id  int8            not null,
    primary key(id)
);

alter table frl_payment_history add constraint FK_payment_history_subscribed_plan foreign key (subscribed_plan_id) references frl_subscribed_plan;
