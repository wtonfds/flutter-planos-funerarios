drop sequence if exists frl_log_client_history_sequence;
create sequence frl_log_client_history_sequence start 1 increment 1;

create table frl_log_client_history
(
    id          int8         not null,
    type        varchar(255) not null,
    description varchar(511) not null,
    date        timestamp    not null,
    primary key (id)
);