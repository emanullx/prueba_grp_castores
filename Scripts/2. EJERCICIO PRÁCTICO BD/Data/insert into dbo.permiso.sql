use castores
go

delete from dbo.permiso
go

insert into dbo.permiso
values
(
	'Ver m�dulo inventario'
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
	'Ver m�dulo para Salida de productos'
	, 'SAL01'
)
, (
	'Sacar inventario del almac�n'
	, 'SAL02'
)
, (
	'Ver m�dulo del hist�rico'
	, 'HIS01'
)	
go