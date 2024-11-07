use castores
go

insert into usuario
select
	'administrador'
	, 'admin@castores.com.mx'
	, '123456789'
	, rol.id
	, 1
from 
	castores.dbo.rol rol
where 
	rol.nombre = 'Administrador'
go

insert into usuario
select
	'Almacenista'
	, 'allma@castores.com.mx'
	, '987654321'
	, rol.id
	, 1
from 
	castores.dbo.rol rol
where 
	rol.nombre = 'Almacenista'

go

