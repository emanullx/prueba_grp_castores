use castores;
go
if exists (select * from sys.objects where type = 'p' and name = 'sp_cast_inventario')
	begin
		drop procedure sp_cast_inventario;
	end;
go

create procedure sp_cast_inventario
    @opcion int
	, @id bigint = null
	, @status int = null
	, @stock int = null
	, @ubicacion varchar(255) = NULL
	, @idProducto bigint = null
	, @next int = null
	, @offset int = null
	, @maxRegPag int = null
	, @idUsuario bigint = null
as
begin

	declare @fecha datetime
		, @tipoMovimiento int
		, @stockAntes int
		, @stockDespues int

	set @fecha = getdate()
	set @maxRegPag = ISNULL(nullif(@maxRegPag, 0), 300)
	set @next = ISNULL(nullif(@next, 0), @maxRegPag)
	set @offset = ISNULL(@offset, 0)

	if(@opcion = 1)
		begin

			select 
				ISNULL(nullif(count(inv.id) / @maxRegPag, 0), 1) paginas
				, count(inv.id) total
			from 
				castores.dbo.inventario inv
			where 
				inv.id = isnull(@id, inv.id)
				and inv.idProducto = isnull(@idProducto, inv.idProducto)
				and inv.status = isnull(@status, inv.status)
				and inv.ubicacion = isnull(@ubicacion, inv.ubicacion)

		end

	if(@opcion = 2)
		begin

			select 
				inv.id
				, inv.status
				, inv.stock
				, inv.ubicacion
				, inv.idProducto
				, prod.nombre
				, prod.descripcion
				, prod.precio
				, prod.urlImagen
			from 
				castores.dbo.inventario inv
			inner join 
				castores.dbo.producto prod
			on
				prod.id = inv.idProducto
			where 
				inv.id = isnull(@id, inv.id)
				and inv.idProducto = isnull(@idProducto, inv.idProducto)
				and inv.status = isnull(@status, inv.status)
				and inv.ubicacion = isnull(@ubicacion, inv.ubicacion)
			order by inv.id
			offset @offset rows
			fetch next @next rows only;

		end
	if(@opcion in (3, 4))
		begin
			
			if not exists(
				select 
					1
				from 
					castores.dbo.producto prod
				where 
					prod.id = @idProducto
			)
				begin

					raiserror (
						N'No existe el producto', 
						16, 
						1);
					return;

				end

			if not exists(
				select 
					1
				from 
					castores.dbo.usuario usu
				where 
					usu.id = @idUsuario
			)
				begin

					raiserror (
						N'No existe el usuario', 
						16, 
						1);
					return;

				end
				
			if(@stock > 0)
				set @status = 1
			else	
				set @status = 0

		end

	if(@opcion = 3)
		begin

			insert into inventario 
			(
				status
				, idProducto
				, stock
				, ubicacion
			)
			values 
			(
				@status
				, @idProducto
				, @stock
				, @ubicacion
			);
		
			select 
				@id = @@identity

			select
				'Return Value' = @id
			
			exec castores.dbo.sp_cast_historico 
				@opcion = 3
				, @ubicacion = @ubicacion
				, @stockAntes = 0
				, @stockDespues = @stock
				, @fechaMovimiento = @fecha
				, @tipoMovimiento = 1
				, @movimiento = @stock
				, @idUsuario = @idUsuario
				, @idProducto = @idProducto 

		end
	
	if(@opcion = 4)
		begin

			select 
				@stockAntes = inv.stock
				, @ubicacion = inv.ubicacion
				, @tipoMovimiento = case when 
										@stock >= 0
									then 1
									else 2
									end
				, @id = inv.id
			from
				castores.dbo.inventario inv
			where
				inv.id = @id

			if(@stockAntes > @stock and  @stock > 0)
				begin

					raiserror (
						N'No puede realizar esta acción.', 
						16, 
						1);
					return;

				end

			if(@stock > 0)
				set @stockDespues = @stock;
			else	
				set @stockDespues = @stockAntes + @stock;
			
			if(@stockDespues > 0)
				set @status = 1;
			else if (@stockDespues = 0)
				set @status = 0;
			else
				begin

					raiserror (
						N'No puede realizar esta acción.', 
						16, 
						1);
					return;

				end

			if(isnull(@id, 0) = 0)
				begin
					
					raiserror (
						N'No existe el inventario', 
						16, 
						1);
					return;

				end

			update 
				inventario
			set
				status = @status
				, stock = @stockDespues
			where
				id = @id

			exec castores.dbo.sp_cast_historico 
				@opcion = 3
				, @ubicacion = @ubicacion
				, @stockAntes = @stockAntes
				, @stockDespues = @stockDespues
				, @fechaMovimiento = @fecha
				, @tipoMovimiento = @tipoMovimiento
				, @movimiento = @stock
				, @idUsuario = @idUsuario
				, @idProducto = @idProducto 

		end

end;
go

grant execute on sp_cast_inventario to public;
go