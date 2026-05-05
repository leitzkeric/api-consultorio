alter table consultas add ativo tinyint;
update consultas set ativo = 1 where motivo_cancelamento is null;
update consultas set ativo = 0 where motivo_cancelamento is not null;
alter table consultas modify ativo tinyint not null;