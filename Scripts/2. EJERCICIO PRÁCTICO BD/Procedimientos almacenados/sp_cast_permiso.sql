use castores;
go

if exists (select * from sys.objects where type = 'p' and name = 'sp_cast_permiso')
	begin
		drop procedure sp_cast_permiso;
	end;
go

create procedure sp_cast_permiso
    @opcion int
	, @id bigint = null
	, @clave varchar(255) = null
	, @descripcion varchar(255) = NULL
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
				castores.dbo.permiso perm
			where 
				perm.id = isnull(@id, perm.id)
				and perm.descripcion like isnull(@descripcion, perm.descripcion)
				and perm.clave = isnull(@clave, perm.clave)

		end

	if(@opcion = 2)
		begin

			select 
				perm.id
				, perm.descripcion
				, perm.clave
			from 
				castores.dbo.permiso perm
			where 
				perm.id = isnull(@id, perm.id)
				and perm.descripcion like isnull(@descripcion, perm.descripcion)
				and perm.clave = isnull(@clave, perm.clave)
			order by perm.id
			offset @offset rows
			fetch next @next rows only;

		end

	if(@opcion = 3)
		begin

			select 
				perm.id
				, perm.descripcion
				, perm.clave
			from 
				castores.dbo.permiso perm
			where 
				perm.id = isnull(@id, perm.id)
				and perm.descripcion like isnull(@descripcion, perm.descripcion)
				and perm.clave = isnull(@clave, perm.clave)
			order by perm.id asc

		end

	if(@opcion = 4)
		begin
			
			if exists(
				select 
					1
				from 
					castores.dbo.permiso perm
				where 
					perm.clave = @clave
			)
				begin

					raiserror (
						N'Ya existe la clave de permiso.', 
						16, 
						1);
					return;

				end

			insert into permiso 
			(
				descripcion
				, clave
			)
			values 
			(
				@descripcion
				, @clave
			);
		
			select 
				'Return Value' = @@identity

		end

end
go

grant execute on sp_cast_permiso to public;
go
