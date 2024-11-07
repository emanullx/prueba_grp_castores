use castores;
go

if exists (select * from sys.objects where type = 'p' and name = 'sp_cast_rol')
	begin
		drop procedure sp_cast_rol;
	end;
go

create procedure sp_cast_rol
    @opcion int
	, @id bigint = null
	, @nombre varchar(255) = null
as
begin
	
	if(@opcion = 1)
		begin

			select 
				rol.id
				, rol.nombre
			from 
				castores.dbo.rol rol
			where 
				rol.id = isnull(@id, rol.id)
				and rol.nombre = isnull(@nombre, rol.nombre)
			order by rol.id

		end

	if(@opcion = 2)
		begin
			
			if exists(
				select 
					1
				from 
					castores.dbo.rol rol
				where 
					rol.nombre = @nombre
			)
				begin

					raiserror (
						N'Ya existe el rol.', 
						16, 
						1);
					return;

				end

			insert into rol 
			(
				nombre
			)
			values 
			(
				@nombre
			);
		
			select 
				'Return Value' = @@identity

		end

end
go

grant execute on sp_cast_rol to public;
go
