use castores
go

set ansi_nulls on
go

set quoted_identifier on
go
if exists (select * from sys.tables where name = 'rol' and type = 'u')
	begin
		drop table dbo.rol;
	end
go
create table dbo.rol(
	id bigint identity(1,1) not null primary key
	, nombre varchar(255) null
)
go


