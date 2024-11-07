use castores;
go

if exists (select * from sys.objects where type = 'p' and name = 'sp_cast_producto')
	begin
		drop procedure sp_cast_producto;
	end;
go

create procedure sp_cast_producto
    @opcion int
	, @id bigint = null
	, @nombre varchar(255) = null
	, @descripcion varchar(255) = null
	, @precio float = NULL
	, @ubicacion varchar(255) = null
	, @urlImagen varchar(255) = null
	, @next int = null
	, @offset int = null
	, @maxRegPag int = null
	, @idUsuario varchar(255) = null
as
begin
	
	set @next = ISNULL(nullif(@next, 0), 1)
	set @offset = ISNULL(@offset, 0)
	set @maxRegPag = ISNULL(@maxRegPag, 300)

	if(@opcion = 1)
		begin

			select 
				ISNULL(nullif(count(prod.id) / @maxRegPag, 0), 1) paginas
				, count(prod.id) total
			from 
				castores.dbo.producto prod
			where 
				prod.id = isnull(@id, prod.id)
				and prod.nombre like isnull(@nombre, prod.nombre) + '%'
				and prod.descripcion like isnull(@descripcion, prod.nombre) + '%'

		end

	if(@opcion = 2)
		begin

			select 
				prod.id
				, prod.nombre
				, prod.precio
				, prod.descripcion
				, prod.urlImagen
			from 
				castores.dbo.producto prod
			where 
				prod.id = isnull(@id, prod.id)
				and prod.nombre like isnull(@nombre, prod.nombre) + '%'
				and prod.descripcion like isnull(@descripcion, prod.nombre) + '%'
			order by prod.id
			offset @offset rows
			fetch next @next rows only;

		end

	if(@opcion = 3)
		begin
			
			if exists(
				select 
					1
				from 
					castores.dbo.producto prod
				where 
					prod.nombre = @nombre
			)
				begin

					raiserror (
						N'Ya existe un producto con el mismo nombre.', 
						16, 
						1);
					return;

				end

			insert into producto 
			(
				 nombre
				, descripcion
				, precio
				, urlImagen
			)
			values 
			(
				@nombre
				, @descripcion
				, @precio
				, @urlImagen
			);
		
			select 
				@id = @@identity

			select 
				'Return Value' = @id

            exec castores.dbo.sp_cast_inventario
				@opcion = 3
				, @stock = 0
				, @ubicacion = @ubicacion
				, @idProducto = @id
				, @idUsuario = @idUsuario

		end

end
go

grant execute on sp_cast_producto to public;
go