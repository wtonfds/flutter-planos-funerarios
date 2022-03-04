drop sequence if exists frl_user_client_one_signal_preference_sequence;
create sequence frl_user_client_one_signal_preference_sequence start 1 increment 1;

drop table if exists frl_user_client_one_signal_preference;

create table frl_user_client_one_signal_preference
(
    id   int8         not null,
    name varchar(255) not null,
    primary key (id)
);

insert into frl_user_client_one_signal_preference
VALUES (nextval('frl_user_client_one_signal_preference_sequence'),
        'INSURANCE');

insert into frl_user_client_one_signal_preference
VALUES (nextval('frl_user_client_one_signal_preference_sequence'),
        'LOTTERY_DRAW');

insert into frl_user_client_one_signal_preference
VALUES (nextval('frl_user_client_one_signal_preference_sequence'),
        'ACCREDITED');

insert into frl_user_client_one_signal_preference
VALUES (nextval('frl_user_client_one_signal_preference_sequence'),
        'UPDATES');

insert into frl_user_client_one_signal_preference
VALUES (nextval('frl_user_client_one_signal_preference_sequence'),
        'DEPENDENT_ACTIVITY');

create table frl_user_client_one_signal_preference_list
(
    client_id                     int8 not null,
    one_signal_preference_list_id int8 not null
);

alter table frl_user_client_one_signal_preference_list
    add constraint FK_client_id foreign key (client_id) references frl_user_client;
alter table frl_user_client_one_signal_preference_list
    add constraint FK_one_signal_preference_list_id foreign key (one_signal_preference_list_id) references frl_user_client_one_signal_preference;