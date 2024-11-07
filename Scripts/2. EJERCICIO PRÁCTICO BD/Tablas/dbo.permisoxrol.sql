use castores
go

set ansi_nulls on
go

set quoted_identifier on
go

if exists (select * from sys.tables where name = 'permisoxrol' and type = 'u')
	begin
		drop table dbo.permisoxrol;
	end
go

create table dbo.permisoxrol(
	id bigint identity(1,1) not null primary key
	, idPermiso bigint null
	, idRol bigint null
	, foreign key(idPermiso) references dbo.permiso (id)
	, foreign key(idRol) references dbo.rol (id)
)
go

