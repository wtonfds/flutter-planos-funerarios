alter sequence frl_address_sequence restart with 1;

insert into frl_address (id, zip_code, street, number, complement, neighborhood, city, province, country)
values (nextval('frl_address_sequence'), '15555-050', 'Rua de exemplo', 42, null, 'Bairro de exemplo', 'São Carlos',
        'SP', 'Brasil'); -- 1

alter sequence frl_user_system_user_sequence restart with 1;

insert into frl_user_farol (id, email, password_hash, temporary_password, name, active, cpf, agent_number, telephone,
                            address_id)
values (nextval('frl_user_system_user_sequence'), 'user1@example.com',
        '$2a$10$Fb3Hx7Ty4FwrEG2ioSboD.pZED3KTAE9ApAae3J5r7rMRh3uYbYx2', false, 'User 1', true, '10047828021', 1,
        '+55 11 4002-8922', null); -- 1, password: abcABC123!@#

insert into frl_user_client (id, email, password_hash, temporary_password, name, active, cpf, birth_day, rg, holder_id,
                             telephone, alive, client_type, created_at, deleted, one_signal_player_id, grace_period)
values (nextval('frl_user_system_user_sequence'), 'user2@example.com',
        '$2a$10$BQDJs6LUhvYBA3.o2hazguNbloZSf9MFP/ebhfaKYDV.mTRyY1Y12', false, 'User 2', true, '31648581080',
        '1992-01-01', '414567389', null, '(16) 91234-5678', 1, 'HOLDER', '2019-12-31 11:01:01', false,
        null, null), -- 2, password: GC*7T3/r4hw'
       (nextval('frl_user_system_user_sequence'), 'user3@example.com',
        '$2a$10$qOqlMZrV9ysclaMFw3pgp.SXn/A8M/O0U.bWrnFFaAieu5YpuUPWO', false, 'User 3', true, '84140163089',
        '1982-01-01', '156928425', null, '(16) 99999-9999', 1, 'HOLDER', '2018-10-10 12:12:12', false,
        null, null), -- 3, password: 9Mu1F'0-xJ.c
       (nextval('frl_user_system_user_sequence'), 'user4@example.com',
        '$2a$10$31MabmSC.o5cFGcooUb9zeuKTMihryZAe1FDIXyhIBkk1h5uzWcga', false, 'User 4', true, '25751290011',
        '1995-01-01', '297144662', null, '(16) 98765-4321', 1, 'HOLDER', '2020-01-15 20:20:20', false,
        null, null); -- 4, password: 6,9#$NBb5baP

alter sequence frl_price_table_sequence restart with 1;

insert into frl_plan_price_table (id, name)
values (nextval('frl_price_table_sequence'), 'Table 1'),       -- 1
       (nextval('frl_price_table_sequence'), 'Table 2'),       -- 2
       (nextval('frl_price_table_sequence'), 'Another table'), -- 3
       (nextval('frl_price_table_sequence'), 'Yet another table'); -- 4

alter sequence frl_price_table_age_range_sequence restart with 1;

insert into frl_price_table_age_range (id, start_age, end_age, value)
values (nextval('frl_price_table_age_range_sequence'), 0, 50, 25000),   -- 1
       (nextval('frl_price_table_age_range_sequence'), 51, 100, 55000), -- 2
       (nextval('frl_price_table_age_range_sequence'), 15, 60, 13000),  -- 3
       (nextval('frl_price_table_age_range_sequence'), 61, 89, 26000),  -- 4
       (nextval('frl_price_table_age_range_sequence'), 0, 120, 100000), -- 5
       (nextval('frl_price_table_age_range_sequence'), 0, 110, 69000); -- 6

insert into frl_plan_price_table_age_ranges (plan_price_table_id, age_ranges_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 6);

alter sequence frl_campaign_sequence restart with 1;

insert into frl_campaign (id, active, name, start_date, end_date, recurrence_type, recurrence, time_to_send, message,
                          delivery_mode, inactive_clients, birthday_clients, children_with_age, expiring_contracts,
                          without_coupons, without_tem, without_funeral_assistance)
values (nextval('frl_campaign_sequence'), true, 'Campaign 1', '2017-01-01', '2025-01-01', 'MONTHLY', 2, '15:00',
        'Example message 1', 'EMAIL', false, false, true, true, false, false, true), -- 1
       (nextval('frl_campaign_sequence'), true, 'Campaign 2', '2018-06-30', '2020-04-18', 'WEEKLY', 5, '09:00',
        'Some other message', 'SMS', true, true, false, false, true, true, false),   -- 2
       (nextval('frl_campaign_sequence'), false, 'Another campaign', '2016-02-13', '2023-11-11', 'DAILY', 7, '19:00',
        'Example message 3', 'EMAIL', false, true, false, true, true, false, true),  -- 3
       (nextval('frl_campaign_sequence'), true, 'Yet another campaign', '2020-01-01', '2024-05-31', 'DAILY', 3, '13:00',
        'Yet another message', 'NOTIFICATION', false, false, false, true, false, true, false); -- 4

alter sequence frl_plan_sequence restart with 1;

insert into frl_plan (id, name, active, grace_period, grace_period_extra_dependents, max_extra_dependents_amount,
                      adhesion_contract, plan_price_table_id, upgrade_price_table_id, contract_url)
values (nextval('frl_plan_sequence'), 'Plano exemplo 1', true, 90, 120, 2, 'Exemplo', null, null,
        'http://example.com'); -- 1

alter sequence frl_subscribed_plan_sequence restart with 1;

insert into frl_subscribed_plan (id, adhesion_contract, subscribed_in, value, client_number, grace_period,
                                 beneficiary_id, active, luck_number, is_default, plan_id, address_id, payment_type,
                                 cancelled_in, last_payment, payment_slip_date, waiting_for_last_payment_date)
values (nextval('frl_subscribed_plan_sequence'), 'Exemplo', '2020-02-01 12:31:45', 1500.5, 42, '2020-07-31', 2, true,
        42, true, 1, 1, 'PAYMENT_SLIP', null, '2020-04-13 16:33:50', 15, false), -- 1
       (nextval('frl_subscribed_plan_sequence'), 'Exemplo 2', '2020-03-15 12:31:45', 2300.0, 18, '2020-07-31', 4, false,
        13, true, 1, 1, 'CREDIT_CARD', null, '2020-04-13 16:33:50', 23, false); -- 2

alter sequence frl_payment_history_sequence restart with 1;

insert into frl_payment_history (id, created_at, order_id, payment_id, payment_slip_id, value, status,
                                 subscribed_plan_id, months, liquidation_date)
values (nextval('frl_payment_history_sequence'), '2020-01-01 13:23:33', '6d2e4380-d8a3-4ccb-9138-c289182818a3',
        '06f256c8-1bbf-42bf-93b4-ce2041bfb87e', 'a760c304-0594-4579-afe2-0c6ecc2d3745', 1234.56, 'PAID', 1, 1,
        '2020-04-13 11:02:03'), -- 1
       (nextval('frl_payment_history_sequence'), '2020-02-10 20:45:43', '173bedfa-50e3-4b82-8c57-6b986f8a1d0f',
        'fce06cef-65cf-4a54-9805-8a6c85cdce2b', '525ccda3-3fbf-41cc-b220-deee96a7284c', 2100.67, 'CONFIRMED', 1, 2,
        null); -- 2
