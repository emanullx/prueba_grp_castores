package com.ejercicio.inventario.castores.Repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ejercicio.inventario.castores.Entities.Constantes;
import com.ejercicio.inventario.castores.Entities.Permiso;
import com.ejercicio.inventario.castores.Mappers.ResultMapper;

@Service
public class PermisoRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

     public Optional<Permiso> obtener(Optional<Long> id, Optional<String> clave) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_PERMISO + " @opcion = :opcion, @id = :id, @clave = :clave ";
        Permiso permiso = new Permiso();
        MapSqlParameterSource params = new MapSqlParameterSource();
         
        params.addValue("opcion", 3);
        params.addValue("id", id.orElse(null));
        params.addValue("clave", clave.orElse(null));

        try{
            permiso = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new BeanPropertyRowMapper<>(Permiso.class)
                );
        } catch (Exception e) {
            return Optional.empty();
        }
        
        return Optional.of(permiso); 
    }

    public Optional<List<Permiso>> obtenerListado() {
        String sql = "EXEC " + Constantes.SP.SP_CAST_PERMISO + " @opcion = :opcion";
        List<Permiso> permisos = List.of();
        MapSqlParameterSource params = new MapSqlParameterSource();
         
        params.addValue("opcion", 3);
        try {
            permisos = namedParameterJdbcTemplate.query(
                    sql, 
                    params, 
                    new BeanPropertyRowMapper<>(Permiso.class)                
            );
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(permisos);
    }
    public Optional<Permiso> crear(Permiso permiso) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_PERMISO + " @opcion = :opcion,  @clave = :clave, @descripcion = :descripcion ";
        MapSqlParameterSource params = new MapSqlParameterSource();
     
        params.addValue("opcion", 4);
        params.addValue("clave", permiso.getClave());
        params.addValue("descripcion", permiso.getDescripcion());

        try {
            Long id = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new ResultMapper()
                );

            permiso.setId(id);
        } catch (Exception e) {
            return Optional.empty();
        }
        
        return Optional.of(permiso); 
    }
}
