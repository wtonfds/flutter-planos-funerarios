drop sequence if exists frl_documents_sequence;
create sequence frl_documents_sequence start 1 increment 1;

create table frl_documents_client (
    id                              int8            not null,
    client_id                       int8            not null,
    document_type                   varchar(255)    not null,
    url_document                    text            not null,
    active                          boolean         not null,
    primary key (id)
);

alter table frl_documents_client add constraint FK_holder_client foreign key (client_id) references frl_user_client;
