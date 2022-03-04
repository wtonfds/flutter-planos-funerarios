drop sequence if exists frl_trial_user_sequence;

create sequence frl_trial_user_sequence start 1 increment 1;

create table frl_trial_user
(
    id         int8         not null,
    email      varchar(255) not null,
    name       varchar(255) not null,
    created_at timestamp    not null,
    primary key (id)
);