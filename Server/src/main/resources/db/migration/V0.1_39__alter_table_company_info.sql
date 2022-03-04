update frl_company_info_table set cnpj = '71661599000152';
update frl_company_info_table set municipal_registry = '8136501';

alter table frl_company_info_table add column city_code varchar(63);
update frl_company_info_table set city_code = '3543402';

alter table frl_company_info_table add column nf_item_code varchar(63);
update frl_company_info_table set nf_item_code = '25.03';

alter table frl_company_info_table add column nf_discrimination varchar(255);
update frl_company_info_table set nf_discrimination = 'Plano de assistencia funeral';

update frl_company_info_table set municipal_registry  = '8136501';

alter table frl_company_info_table add column nf_municipal_tax_code varchar(255);
update frl_company_info_table set nf_municipal_tax_code = '25.03 / 00250300';

alter table frl_company_info_table add column nf_share float;
update frl_company_info_table set nf_share = 0.04;

alter table frl_company_info_table add column rps_identification int8;
update frl_company_info_table set rps_identification = 1;