use castores;
go

if exists (select * from sys.objects where type = 'p' and name = 'sp_cast_historico')
	begin
		drop procedure sp_cast_historico;
	end;
go

create procedure sp_cast_historico
    @opcion int
	, @id bigint = null
	, @ubicacion nvarchar(255) = null
	, @stockAntes int = null
	, @stockDespues int = null
	, @fechaMovimiento datetime = null
	, @tipoMovimiento int = null
	, @movimiento int = null
	, @idUsuario bigint = null
	, @idProducto bigint = null
	, @next int = null
	, @offset int = null
	, @maxRegPag int = null
as
begin

	set @maxRegPag = ISNULL(nullif(@maxRegPag, 0), 300)
	set @next = ISNULL(nullif(@next, 0), @maxRegPag)
	set @offset = ISNULL(@offset, 0)

	if(@opcion = 1)
		begin

			select 
				ISNULL(nullif(count(hist.id) / @maxRegPag, 0), 1) paginas
				, count(hist.id) total
			from 
				castores.dbo.historico hist
			where 
				hist.id = isnull(@id, hist.id)
				and hist.idProducto = isnull(@idProducto, hist.idProducto)
				and hist.ubicacion = isnull(@ubicacion, hist.ubicacion)
				and hist.idUsuario = isnull(@idUsuario, hist.idUsuario)

		end

	if(@opcion = 2)
		begin

			select 
				hist.id
				, hist.ubicacion
				, hist.stockAntes
				, hist.stockDespues
				, hist.fechaMovimiento
				, hist.tipoMovimiento
				, hist.movimiento
				, hist.idUsuario
				, hist.idProducto
				, prod.nombre
				, prod.descripcion
				, prod.precio
				, prod.urlImagen
				, us.nombre nombreUsuario
			from 
				castores.dbo.historico hist
			inner join 
				castores.dbo.producto prod
			on
				prod.id = hist.idProducto
			inner join 
				castores.dbo.usuario us
			on 
				us.id = hist.idUsuario
			where 
				hist.id = isnull(@id, hist.id)
				and hist.idProducto = isnull(@idProducto, hist.idProducto)
				and hist.ubicacion = isnull(@ubicacion, hist.ubicacion)
				and hist.idUsuario = isnull(@idUsuario, hist.idUsuario)
			order by hist.id
			offset @offset rows
			fetch next @next rows only;

		end

	if(@opcion = 3)
		begin

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

			insert into Historico 
			(
				ubicacion
				, stockAntes
				, stockDespues
				, fechaMovimiento
				, tipoMovimiento
				, movimiento
				, idUsuario
				, idProducto
			)
			values 
			(
				@ubicacion
				, @stockAntes
				, @stockDespues
				, @fechaMovimiento
				, @tipoMovimiento
				, @movimiento
				, @idUsuario
				, @idProducto
			);
		
			select 
				'Return Value' = @@identity
		end
	
   
end;
go

grant execute on sp_cast_historico to public;
go
