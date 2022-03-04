drop table if exists frl_plan_price_table_age_ranges;
create table frl_plan_price_table_age_ranges
(
    plan_price_table_id int8 not null,
    age_ranges_id int8 not null
);

drop table if exists frl_upgrade_price_table_age_ranges;
create table frl_upgrade_price_table_age_ranges
(
    age_ranges_id int8 not null,
    upgrade_price_table_id int8 not null
);

drop sequence if exists frl_price_table_sequence;
create sequence frl_price_table_sequence start 1 increment 1;

drop table if exists frl_plan_price_table cascade;
create table frl_plan_price_table
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);


drop table if exists frl_upgrade_price_table cascade;
create table frl_upgrade_price_table
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);

drop sequence if exists frl_price_table_age_range_sequence;
create sequence frl_price_table_age_range_sequence start 1 increment 1;

drop table if exists frl_price_table_age_range;
create table frl_price_table_age_range
(
    id                 int8          not null,
    start_age          int4          not null,
    end_age            int4          not null,
    value              numeric(8, 2) not null, -- 6 integer, 2 decimal
    primary key (id)
);

alter table frl_upgrade_price_table_age_ranges add constraint FK_upgrade_price_table_age_ranges foreign key (age_ranges_id) references frl_price_table_age_range;
alter table frl_upgrade_price_table_age_ranges add constraint FK_upgrade_price_table foreign key (upgrade_price_table_id) references frl_upgrade_price_table;

alter table frl_plan_price_table_age_ranges add constraint FK_plan_price_table_age_ranges foreign key (age_ranges_id) references frl_price_table_age_range;
alter table frl_plan_price_table_age_ranges add constraint FK_plan_price_table foreign key (plan_price_table_id) references frl_plan_price_table;
