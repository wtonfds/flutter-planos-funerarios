drop sequence if exists frl_user_login_session_sequence;
drop sequence if exists  frl_user_system_user_sequence;

create sequence frl_user_login_session_sequence start 1 increment 1;
create sequence frl_user_system_user_sequence start 1 increment 1;

drop table if exists frl_user_client;
create table frl_user_client
(
    id                 int8            not null,
    email              varchar(255),   -- not null for holder, but not for dependent
    password_hash      varchar(255),   -- not null for holder, but not for dependent
    temporary_password boolean,        -- not null for holder, but not for dependent
    name               varchar(255)    not null,
    active             boolean         not null,
    cpf                varchar(15)     not null,
    birth_day          date,
    rg                 varchar(18),    -- not null for dependents and complete client
    holder_id          int8,
    telephone          varchar(16),    -- not null for holder, but not for dependent
    alive              boolean         not null,
    client_type        varchar(32)     not null,
    created_at         timestamp       not null,
    deleted            boolean         default false not null,
    one_signal_player_id varchar(255),
    grace_period       date,
    primary key (id)
);

create index frl_user_clientUserSystemUserEmail on frl_user_client (email);
alter table frl_user_client add constraint UK_user_client_email unique (email);
alter table frl_user_client add constraint UK_user_cpf unique (cpf);
alter table frl_user_client add constraint FK_client_on_holder_id foreign key (holder_id) references frl_user_client;

drop table if exists frl_user_login_session;
create table frl_user_login_session
(
    id                      int8         not null,
    auth_token              varchar(255) not null,
    notification_token      varchar(511),
    valid_until_millis      int8         not null,
    system_user_id          int8,
    primary key (id)
);

create index authTokenIndex on frl_user_login_session (auth_token);
create index validUntilMillisIndex on frl_user_login_session (valid_until_millis);
alter table frl_user_login_session add constraint UK_user_lsession_authtk unique (auth_token);

alter table if exists frl_accredited drop constraint if exists FK_accredited_address;
alter table if exists frl_product drop constraint if exists FK_accredited;
drop table if exists frl_accredited;
drop sequence if exists  frl_accredited_sequence;

drop sequence if exists frl_address_sequence;
create sequence frl_address_sequence start 1 increment 1;

drop table if exists frl_address;
create table frl_address (
     id                 int8            not null,
     zip_code           varchar(255)    not null,
     street             varchar(255)    not null,
     number             varchar(255)    not null,
     complement         varchar(255),
     neighborhood       varchar(255)    not null,
     city               varchar(255)    not null,
     province           varchar(255)    not null,
     country            varchar(255)    not null,
     primary key (id)
);

drop table if exists frl_user_farol cascade ;

drop sequence if exists  frl_user_farol_agent_number_seq;
create sequence frl_user_farol_agent_number_seq start 1 increment 1;

create table frl_user_farol (
    id                          int8            not null,
    email                       varchar(255)    not null,
    password_hash               varchar(255)    not null,
    temporary_password          boolean         not null,
    name                        varchar(255)    not null,
    active                      boolean         not null,
    cpf                         varchar(15)     not null,
    agent_number                int8            not null,
    telephone                   varchar(16)     not null,
    address_id                  int8,
    primary key (id)
);

create index frl_user_farolUserSystemUserEmail on frl_user_farol (email);
alter table frl_user_farol add constraint UK_user_farol_email unique (email);
alter table frl_user_farol add constraint UK_user_farol_cpf unique (cpf);
alter table frl_user_farol add constraint UK_user_farol_agent_number unique (agent_number);

create table frl_accredited (
    id                      int8                not null,
    name                    varchar(255)        not null,
    cnpj                    varchar(19)         not null,
    address_id              int8                not null,
    logo                    varchar(255),
    coupon_origin           varchar(255)        not null,
    code                    varchar(255),
    active                  boolean,
    category                varchar(255),
    subcategory             varchar(255),
    telephone               varchar(16)         not null,
    email                   varchar(255),
    password_hash           varchar(255),
    temporary_password      boolean             not null,
    primary key (id)
);

create sequence frl_accredited_sequence start 1 increment 1;
alter table frl_accredited add constraint FK_accredited_address foreign key (address_id) references frl_address;
alter table frl_accredited add constraint UK_accredited_email unique (email);
alter table frl_accredited add constraint UK_accredited_cnpj unique (cnpj);


drop table if exists frl_general;

drop sequence if exists frl_general_sequence;
create sequence frl_general_sequence start 1 increment 1;

create table frl_general (
    id                              int8            not null,
    loyalty_card                    boolean         not null,
    loyalty_card_number_rule        varchar(512)    not null,
    tem                             boolean         not null,
    accredited_login_with_cnpj      boolean         not null,
    accredited_coupon_due_date      date            not null,
    lottery_auto_disclosure         boolean         not null,
    lottery_url                     varchar(512)    not null,
    sla                             varchar(255)    not null,
    nf_iss                          varchar(255)    not null,
    nf_giss_url                     varchar(255)    not null,
    nf_giss_user                    varchar(255)    not null,
    nf_giss_password                varchar(255)    not null,
    nf_giss_due_date                varchar(255)    not null,
    farol_telephone                 varchar(255)    not null,
    time_to_block_account           integer         not null,
    time_to_update_financial_data   integer         not null,
    farol_email                     varchar(255)    not null,
    funeral_assistance_phone        varchar(255)    not null,
    primary key (id)
);

insert into frl_general VALUES (
    nextval('frl_general_sequence'),
    true,
    '',
    true,
    true,
    '20000101',
    false,
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    '',
    45,
    2,
    '',
    ''
);

alter table if exists frl_product drop constraint if exists FK_user_farol;

drop table if exists frl_product;

drop sequence if exists frl_product_sequence;
create sequence frl_product_sequence start 1 increment 1;

create table frl_product (
    id                              int8            not null,
    accredited_id                   int8            not null,
    created_by_id                   int8            not null,
    name                            varchar(255)    not null,
    discount                        varchar(255),
    due_date                        date,
    primary key (id)
);

alter table frl_product add constraint FK_accredited foreign key (accredited_id) references frl_accredited;
alter table frl_product add constraint FK_user_farol foreign key (created_by_id) references frl_user_farol;

drop table if exists frl_plan;

create table frl_plan (
    id                              int8                not null,
    name                            varchar(255)        not null,
    active                          boolean             not null,
    grace_period                    int8                not null,
    grace_period_extra_dependents   int8                not null,
    max_extra_dependents_amount     int4                not null,
    adhesion_contract               varchar(511)        not null,
    plan_price_table_id             int8,
    upgrade_price_table_id          int8,
    contract_url                    varchar(511)        not null,
    primary key (id)
);

drop sequence if exists frl_plan_sequence;
create sequence frl_plan_sequence start 1 increment 1;
