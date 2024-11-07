use castores;
go

if exists (select * from sys.objects where type = 'p' and name = 'sp_cast_usuario')
	begin
		drop procedure sp_cast_usuario;
	end;
go

create procedure sp_cast_usuario
    @opcion int
	, @id bigint = null
	, @nombre varchar(255) = null
	, @correo varchar(255) = NULL
	, @contraseña varchar(255) = NULL
	, @idRol bigint = null
	, @status int = null
as
begin

	if(@opcion = 1)
		begin

			select 
				usu.id
				, usu.nombre
				, usu.correo
				, usu.contraseña
				, usu.idRol
				, usu.status
			from 
				castores.dbo.usuario usu
			where 
				usu.id = isnull(@id, usu.id)
				and usu.nombre = isnull(@nombre, usu.nombre)
				and usu.correo = isnull(@correo, usu.correo)
				and usu.idRol = isnull(@idRol, usu.idRol)
				and usu.status = isnull(@status, usu.status)
			order by usu.id

		end

	if(@opcion = 2)
		begin
			
			if exists(
				select 
					1
				from 
					castores.dbo.usuario usu
				where 
					usu.nombre = @nombre
					or usu.correo = @correo
			)
				begin

					raiserror (
						N'Ya existe un usuario registrado con los datos ingresados.', 
						16, 
						1);
					return;

				end

			insert into usuario 
			(
				nombre
				, correo
				, contraseña
				, idRol
				, status
			)
			values 
			(
				@nombre
				, @correo
				, @contraseña
				, @idRol
				, 1
			);
		
			select 
				'Return Value' = @@identity

		end

end
go

grant execute on sp_cast_usuario to public;
go
