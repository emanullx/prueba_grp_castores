use castores
go

set ansi_nulls on
go

set quoted_identifier on
go

if exists (select * from sys.tables where name = 'inventario' and type = 'u')
	begin
		drop table dbo.inventario;
	end

create table dbo.inventario(
	id bigint identity(1,1) not null primary key
	, status int not null
	, stock int not null
	, ubicacion varchar(255) null
	, idProducto bigint null
	, foreign key(idProducto) references dbo.producto (id)
)
go


