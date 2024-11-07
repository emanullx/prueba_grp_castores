use castores;
go

if exists (select * from sys.objects where type = 'p' and name = 'sp_cast_permisoxrol')
	begin
		drop procedure sp_cast_permisoxrol;
	end;
go

create procedure sp_cast_permisoxrol
    @opcion int
	, @id bigint = null
	, @idPermiso bigint = null
	, @idRol bigint = NULL
	, @next int = null
	, @offset int = null
	, @maxRegPag int = null
as
begin
	
	set @next = ISNULL(nullif(@next, 0), 1)
	set @offset = ISNULL(@offset, 0)
	set @maxRegPag = ISNULL(@maxRegPag, 300)

	if(@opcion = 1)
		begin

			select 
				ISNULL(nullif(count(perm.id) / @maxRegPag, 0), 1) paginas
				, count(perm.id) total
			from 
				castores.dbo.permisoxrol perm
			where 
				perm.id = isnull(@id, perm.id)
				and perm.idPermiso like isnull(@idPermiso, perm.idPermiso)
				and perm.idRol = isnull(@idRol, perm.idRol)

		end

	if(@opcion = 2)
		begin

			select 
				perm.id
				, perm.idPermiso
				, perm.idRol
				, prm.clave
				, prm.descripcion
			from 
				castores.dbo.permisoxrol perm
			inner join 
				castores.dbo.permiso prm
			on 
				perm.idPermiso = prm.id
			where 
				perm.id = isnull(@id, perm.id)
				and perm.idPermiso like isnull(@idPermiso, perm.idPermiso)
				and perm.idRol = isnull(@idRol, perm.idRol)
			order by perm.id
			offset @offset rows
			fetch next @next rows only;

		end

	if(@opcion = 3)
		begin

			select 
				perm.id
				, perm.idPermiso
				, perm.idRol
				, prm.clave
				, prm.descripcion
			from 
				castores.dbo.permisoxrol perm
			inner join 
				castores.dbo.permiso prm
			on 
				perm.idPermiso = prm.id
			where 
				perm.id = isnull(@id, perm.id)
				and perm.idPermiso like isnull(@idPermiso, perm.idPermiso)
				and perm.idRol = isnull(@idRol, perm.idRol)
			order by perm.id

		end

	if(@opcion = 4)
		begin

			if not exists(
				select 
					1
				from 
					castores.dbo.rol rol
				where 
					rol.id = @idRol
			)
				begin

					raiserror (
						N'No existe el rol', 
						16, 
						1);
					return;

				end

			if not exists(
				select 
					1
				from 
					castores.dbo.permiso perm
				where 
					perm.id = @idPermiso
			)
				begin

					raiserror (
						N'No existe el permiso', 
						16, 
						1);
					return;

				end
			
			if exists(
				select 
					1
				from 
					castores.dbo.permisoxrol perm
				where 
					perm.idPermiso = @idPermiso
					and perm.idRol = @idRol
			)
				begin

					raiserror (
						N'El permiso ya fue asignado con anterioridad al rol.', 
						16, 
						1);
					return;

				end

			insert into permisoxrol 
			(
				idPermiso
				, idRol
			)
			values 
			(
				@idPermiso
				, @idRol
			);
		
			select 
				'Return Value' = @@identity

		end

end
go

grant execute on sp_cast_permisoxrol to public;
go
