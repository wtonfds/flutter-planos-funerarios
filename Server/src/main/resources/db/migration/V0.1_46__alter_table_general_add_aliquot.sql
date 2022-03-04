alter table frl_general
    add column aliquot varchar(20);

update frl_general set aliquot = '0.04';
