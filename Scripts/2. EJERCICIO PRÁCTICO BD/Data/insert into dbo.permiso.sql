use castores
go

delete from dbo.permiso
go

insert into dbo.permiso
values
(
	'Ver módulo inventario'
	, 'INV01'
)
, (
	'Agregar nuevos productos'
	, 'INV02'
)
, (
	'Aumentar inventario'
	, 'INV03'
)
, (
	'Dar de baja/reactivar un producto'
	, 'INV04'
)
, (
	'Ver módulo para Salida de productos'
	, 'SAL01'
)
, (
	'Sacar inventario del almacén'
	, 'SAL02'
)
, (
	'Ver módulo del histórico'
	, 'HIS01'
)	
go