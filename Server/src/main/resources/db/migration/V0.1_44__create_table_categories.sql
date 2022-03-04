drop sequence if exists frl_category_sequence;
create sequence frl_category_sequence start 1 increment 1;

drop table if exists frl_category;

create table frl_category
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);

insert into frl_category (id, name)
values (1, 'Alimentação');
