use castores
go

set ansi_nulls on
go

set quoted_identifier on
go

if exists (select * from sys.tables where name = 'permiso' and type = 'u')
	begin
		drop table dbo.permiso;
	end

create table dbo.permiso(
	id bigint identity(1,1) not null primary key
	, descripcion varchar(255) null
	, clave varchar(255) null
)
go


