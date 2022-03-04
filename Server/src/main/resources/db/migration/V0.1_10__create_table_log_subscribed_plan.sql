drop sequence if exists frl_log_subscribed_plan_sequence;
create sequence frl_log_subscribed_plan_sequence start 1 increment 1;

create table frl_log_subscribed_plan (
     id                         int8            not null,
     user_id                    int8            not null,
     subscribed_plan_id         int8            not null,
     reactivation_date          timestamp       not null,
     commentary                 varchar(511)    not null,
     primary key (id)
);

alter table frl_log_subscribed_plan add constraint FK_subscribed_plan foreign key (subscribed_plan_id) references frl_subscribed_plan;
alter table frl_log_subscribed_plan add constraint FK_user foreign key (user_id) references frl_user_farol;