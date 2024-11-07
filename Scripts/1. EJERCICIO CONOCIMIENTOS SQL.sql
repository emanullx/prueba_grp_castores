create table productos
(
	idProducto int not null primary key
	, nombre varchar(40) not null
	, precio numeric(16,2) not null
)
go

create table ventas
(
	idVenta int not null primary key
	, idProducto int not null
	, foreign key (idProducto) references productos(idProducto)
	, cantidad int
)

go

insert into productos
values
(
	1
	, 'LAPTOP'
	, 3000.00
)
, (
	2
	, 'PC'
	, 4000.00
)
, (
	3
	, 'MOUSE'
	, 100.00
)
, (
	4
	, 'TECLADO'
	, 150.00
)
, (
	5
	, 'MONITOR'
	, 2000.00
)
, (
	6
	, 'MICROFONO'
	, 350.00
)
, (
	7
	, 'AUDIFONOS'
	, 450.00
)
go

insert into ventas
values
(
	1
	, 5
	, 8
)
, (
	2
	, 1
	, 15
)
, (
	3
	, 6
	, 13
)
, (
	4
	, 6
	, 4
)
, (
	5
	, 2
	, 3
)
, (
	6
	, 5
	, 1
)
, (
	7
	, 4
	, 5
)
, (
	8
	, 2
	, 5
)
, (
	9
	, 6
	, 2
)
, (
	10
	, 1
	, 8
)
go

/*
1.5) Traer todos los productos que tengan una venta.
*/

select
	  prod.idProducto 
	, max(prod.nombre) nombre
	, max(prod.precio) precio
from 
	productos prod
inner join 
	ventas vent
on 
	prod.idProducto = vent.idProducto
group by 
	prod.idProducto 
having
	count(prod.idProducto) = 1

/*
 1.6) Traer todos los productos que tengan ventas y la cantidad total de productos vendidos.
*/

select
	  prod.idProducto 
	, max(prod.nombre) nombre
	, max(prod.precio) precio
	, sum(vent.cantidad) cantidad
from 
	productos prod
inner join 
	ventas vent
on 
	prod.idProducto = vent.idProducto
group by 
	prod.idProducto 

/*
  1.7) Traer todos los productos (independientemente de si tienen ventas o no) y la suma total ($) vendida por
 producto.
*/

select
	  prod.idProducto 
	, max(prod.nombre) nombre
	, max(prod.precio) precio
	, isnull(sum(vent.cantidad), 0) cantidad
	, isnull(sum(vent.cantidad), 0) * max(prod.precio) sumaTotal
from 
	productos prod
full outer join 
	ventas vent
on 
	prod.idProducto = vent.idProducto
group by 
	prod.idProducto 