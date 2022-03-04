drop sequence if exists frl_payment_discount_sequence;

create sequence frl_payment_discount_sequence start 1 increment 1;

create table frl_payment_discount
(
    id         int8          not null,
    month      int4          not null,
    discount   numeric(8, 2) not null,
    primary key (id)
);