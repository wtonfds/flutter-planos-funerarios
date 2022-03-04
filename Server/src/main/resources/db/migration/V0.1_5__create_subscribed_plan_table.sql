drop sequence if exists frl_subscribed_plan_sequence;
create sequence frl_subscribed_plan_sequence start 1 increment 1;

create table frl_subscribed_plan (
    id                              int8            not null,
    adhesion_contract               text            not null,
    subscribed_in                   timestamp       not null,
    value                           float           not null,
    client_number                   int8            not null,
    grace_period                    date            not null,
    beneficiary_id                  int8            not null,
    active                          boolean         not null,
    luck_number                     int8            not null,
    is_default                      boolean         not null,
    plan_id                         int8            not null,
    address_id                      int8            not null,
    cancelled_in                    timestamp,
    last_payment                    timestamp       not null,
    primary key (id)
);

alter table frl_subscribed_plan add constraint FK_subscribed_plan_address foreign key (address_id) references  frl_address;
alter table frl_subscribed_plan add constraint FK_plan foreign key (plan_id) references frl_plan;
alter table frl_subscribed_plan add constraint FK_user_client foreign key (beneficiary_id) references frl_user_client;
