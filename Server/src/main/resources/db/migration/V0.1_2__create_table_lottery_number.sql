drop sequence if exists frl_lottery_number_sequence;
alter table if exists frl_lottery_number drop constraint if exists FK_farol_user_on_approved_by;
drop table if exists frl_lottery_number;

create sequence frl_lottery_number_sequence start 1 increment 1;

create table frl_lottery_number (
    id                   int8            not null,
    original_number1     varchar(10)     not null,
    original_number2     varchar(10)     not null,
    original_number3     varchar(10)     not null,
    original_number4     varchar(10)     not null,
    original_number5     varchar(10)     not null,
    generated_numbers    varchar(10)     not null,
    draw_day             date            not null,
    approved_by_id       int8,
    primary key (id)
);

alter table frl_lottery_number add constraint FK_farol_user_on_approved_by foreign key (approved_by_id) references frl_user_farol;
alter table frl_lottery_number add constraint UK_draw_day unique (draw_day);

update frl_general set lottery_url = 'http://loterias.caixa.gov.br/wps/portal/loterias/landing/federal/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbz8vTxNDRy9_Y2NQ13CDA0MzIAKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wBmoxN_FydLAGAgNTKEK8DkRrACPGwpyQyMMMj0VAYe29yM!/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0K0L710QUKB6OH80004/res/id=buscaResultado';
