use castores
go

set ansi_nulls on
go

set quoted_identifier on
go

if exists (select * from sys.tables where name = 'historico' and type = 'u')
	begin
		drop table dbo.historico;
	end
go

create table dbo.historico(
	id bigint identity(1,1) not null primary key
	, fechaMovimiento datetime2(6) null
	, movimiento int not null
	, stockAntes int not null
	, stockDespues int not null
	, tipoMovimiento int not null
	, ubicacion varchar(255) null
	, idProducto bigint null
	, idUsuario bigint null
	, foreign key(idProducto) references dbo.producto (id)
	, foreign key(idUsuario) references dbo.usuario (id)
)
go



