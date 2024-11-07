use castores
go

set ansi_nulls on
go

set quoted_identifier on
go

if exists (select * from sys.tables where name = 'producto' and type = 'u')
	begin
		drop table dbo.producto;
	end
go

create table dbo.producto(
	id bigint identity(1,1) not null primary key
	, nombre varchar(255) null
	, descripcion varchar(255) null
	, precio bigint null
	, urlImagen varchar(255) null
)
go
