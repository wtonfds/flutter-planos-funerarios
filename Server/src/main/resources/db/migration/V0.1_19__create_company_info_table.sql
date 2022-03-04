drop table if exists frl_company_info_table;
create table frl_company_info_table
(
    name                varchar(255)    not null,
    cnpj                varchar(255)    not null,
    municipal_registry  varchar(255)    not null,
    type                varchar(255)    not null,
    email               varchar(255)    not null,
    telephone           varchar(255)    not null,
    web_site            varchar(255),
    zip_code            varchar(255)    not null,
    street              varchar(255)    not null,
    number              varchar(255),
    complement          varchar(255),
    neighborhood        varchar(255),
    city                varchar(255)    not null,
    province            varchar(255)    not null,
    country             varchar(255)    not null,
    primary key (cnpj)
);

insert into frl_company_info_table VALUES (
    'Vida Plano',
    '86.119.859/0001-30',
    '453.341.056.063',
    'Funerária',
    'vida-plano@vidaplano.com.br',
    '(16) 3304-8976',
    'www.vidaplano.com.br',
    '13560-190',
    'Rua Episcopal',
    '2198',
    '',
    'Jd Bandeirantes',
    'Ribeirão Preto',
    'SP',
    'Brasil'
);