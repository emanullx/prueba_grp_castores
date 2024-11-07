package com.ejercicio.inventario.castores.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.ejercicio.inventario.castores.Entities.Constantes;
import com.ejercicio.inventario.castores.Entities.PermisoXRol;
import com.ejercicio.inventario.castores.Mappers.PermisoXRolMapper;
import com.ejercicio.inventario.castores.Mappers.ResultMapper;

@Service
public class PermisoXRolRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

     public Optional<PermisoXRol> obtener(Long id) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_PERMISOXROL + " @opcion = :opcion, @id = :id ";
        PermisoXRol permisoXRol = new PermisoXRol();
        MapSqlParameterSource params = new MapSqlParameterSource();
         
        params.addValue("opcion", 3);
        params.addValue("id", id);
        try{
            permisoXRol = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new  PermisoXRolMapper()
                );
        } catch (Exception e) {
            return Optional.empty();
        }
        
        return Optional.of(permisoXRol); 
    }

    public Optional<List<PermisoXRol>> obtenerListado(Optional<Long> idRol, Optional<Long> idPermiso) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_PERMISOXROL + " @opcion = :opcion, @idRol = :idRol, @idPermiso = :idPermiso";
        List<PermisoXRol> permisos = List.of();
        MapSqlParameterSource params = new MapSqlParameterSource();
         
        params.addValue("opcion", 3);
        params.addValue("idRol", idRol.orElse(null));
        params.addValue("idPermiso", idPermiso.orElse(null));
        try {
            permisos = namedParameterJdbcTemplate.query(
                    sql, 
                    params, 
                    new PermisoXRolMapper()
                );
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(permisos);
    }

    public Optional<PermisoXRol> crear(PermisoXRol permisoXRol) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_PERMISOXROL + " @opcion = :opcion, @idPermiso = :idPermiso, @idRol = :idRol ";
        MapSqlParameterSource params = new MapSqlParameterSource();
     
        params.addValue("opcion", 4);
        params.addValue("idPermiso", permisoXRol.getPermiso().getId());
        params.addValue("idRol", permisoXRol.getPermiso().getId());

        try {
            Long id = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new ResultMapper()
                );

            permisoXRol.setId(id);
        } catch (Exception e) {
            return Optional.empty();
        }
        
        return Optional.of(permisoXRol); 
    }
}