drop sequence if exists frl_farol_user_permission_sequence;
create sequence frl_farol_user_permission_sequence start 1 increment 1;

drop table if exists frl_farol_user_permission;

create table frl_farol_user_permission
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);

insert into frl_farol_user_permission
VALUES (nextval('frl_farol_user_permission_sequence'),
        'ACCREDITED');

insert into frl_farol_user_permission
VALUES (nextval('frl_farol_user_permission_sequence'),
        'GENERAL_PARAMETERIZATION');

insert into frl_farol_user_permission
VALUES (nextval('frl_farol_user_permission_sequence'),
        'PRICE_TABLE');

create table frl_user_farol_permission_list
(
    farol_user_id      int8 not null,
    permission_list_id int8 not null
);

alter table frl_user_farol_permission_list
    add constraint FK_farol_user_id foreign key (farol_user_id) references frl_user_farol;
alter table frl_user_farol_permission_list
    add constraint FK_permission_list_id foreign key (permission_list_id) references frl_farol_user_permission;