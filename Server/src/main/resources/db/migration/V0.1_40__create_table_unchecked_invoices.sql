drop sequence if exists frl_unchecked_invoice_sequence;
create sequence frl_unchecked_invoice_sequence start 1 increment 1;

drop table if exists frl_unchecked_invoice;

create table frl_unchecked_invoice (
    id                  int8            not null,
    payment_history_id  int8            not null,
    error               boolean,
    primary key(id)
);

alter table frl_unchecked_invoice add constraint FK_unchecked_invoice_payment_history foreign key (payment_history_id) references frl_payment_history;

alter table frl_payment_history add column invoice_protocol_number varchar(255);