drop sequence if exists frl_campaign_sequence;
create sequence frl_campaign_sequence start 1 increment 1;

drop type if exists frl_campaign_recurrence_type cascade;
create type frl_campaign_recurrence_type as enum ('DAILY', 'WEEKLY', 'MONTHLY');

drop type if exists frl_campaign_delivery_mode cascade;
create type frl_campaign_delivery_mode as enum ('EMAIL', 'SMS', 'NOTIFICATION');

drop table if exists frl_campaign;
create table frl_campaign
(
    id                         int8                         not null,
    active                     boolean                      not null,
    name                       varchar(255)                 not null,
    start_date                 date                         not null,
    end_date                   date                         not null,
    recurrence_type            frl_campaign_recurrence_type not null,
    recurrence                 int4                         not null, -- How many times per period (defined by recurrence_type) should the campaign be sent?
    time_to_send               time                         not null, -- When to send this campaign according to the recurrence
    message                    text                         not null,
    delivery_mode              frl_campaign_delivery_mode   not null,

    -- Filters
    inactive_clients           boolean                      not null,
    birthday_clients           boolean                      not null,
    children_with_age          boolean                      not null, -- Clients with children completing a defined age (e.g. 30 years)
    expiring_contracts         boolean                      not null, -- Clients with contracts about to expire
    without_coupons            boolean                      not null, -- Clients that have never used a coupon
    without_tem                boolean                      not null, -- Clients that have never used a TEM
    without_funeral_assistance boolean                      not null, -- Clients that have never used a funeral assistance

    primary key (id)
);
