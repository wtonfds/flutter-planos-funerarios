alter table frl_plan add column dependent_price_table_id int8;

drop table if exists frl_dependent_price_table_age_ranges;
create table frl_dependent_price_table_age_ranges
(
    dependent_price_table_id int8 not null,
    age_ranges_id int8 not null
);

drop table if exists frl_dependent_price_table cascade;
create table frl_dependent_price_table
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);

alter table frl_dependent_price_table_age_ranges add constraint FK_frl_dependent_price_table_age_ranges_frl_price_table_age_range foreign key (age_ranges_id) references frl_price_table_age_range;
alter table frl_dependent_price_table_age_ranges add constraint FK_frl_dependent_price_table_age_ranges_frl_dependent_price_table foreign key (dependent_price_table_id) references frl_dependent_price_table;
