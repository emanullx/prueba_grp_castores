use castores
go

set ansi_nulls on
go

set quoted_identifier on
go

if exists (select * from sys.tables where name = 'usuario' and type = 'u')
	begin
		drop table dbo.usuario;
	end
go

create table dbo.usuario(
	id bigint identity(1,1) not null primary key
	, nombre varchar(255) null
	, correo varchar(255) null
	, contraseña varchar(255) null
	, idRol bigint null
	, status int null
	, foreign key(idRol) references dbo.rol (id)
)
go
