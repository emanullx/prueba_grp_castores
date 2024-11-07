
use castores
go

insert into permisoxrol
select
	perm.id
	, rol.id
from
	castores.dbo.permiso perm, castores.dbo.rol rol
where 
	perm.clave in('INV01', 'INV02', 'INV03', 'INV04', 'HIS01')
	and rol.nombre = 'Administrador'
go

insert into permisoxrol
select
	perm.id
	, rol.id
from
	castores.dbo.permiso perm, castores.dbo.rol rol
where 
	perm.clave in('INV01', 'SAL01', 'SAL02')
	and rol.nombre = 'Almacenista'
go

	



