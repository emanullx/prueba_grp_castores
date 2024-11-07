package com.ejercicio.inventario.castores.Repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ejercicio.inventario.castores.Entities.Constantes;
import com.ejercicio.inventario.castores.Entities.Rol;
import com.ejercicio.inventario.castores.Mappers.ResultMapper;

@Service
public class RolRepository {
        @Autowired
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        public Optional<Rol> obtener(Optional<Long> id, Optional<String> nombre) {
                String sql = "EXEC " + Constantes.SP.SP_CAST_ROL + " :opcion, :id, :nombre ";
                Rol rol = new Rol();
                MapSqlParameterSource params = new MapSqlParameterSource();
                
                params.addValue("opcion", 1);
                params.addValue("id", id.orElse(null));
                params.addValue("nombre", nombre.orElse(null));

                try {
                        rol = namedParameterJdbcTemplate.queryForObject(
                        sql, 
                        params, 
                        new BeanPropertyRowMapper<>(Rol.class)
                        );
                } catch (Exception e) {
                return Optional.empty();
                }
                
                return Optional.of(rol); 
        }

        public Optional<List<Rol>> obtenerListado() {
                String sql = "EXEC " + Constantes.SP.SP_CAST_ROL + " @opcion = :opcion";
  
                MapSqlParameterSource params = new MapSqlParameterSource();
                List<Rol> roles = List.of();
                params.addValue("opcion", 1);

                try {
                        roles = namedParameterJdbcTemplate.query(
                                sql, 
                                params, 
                                new BeanPropertyRowMapper<>(Rol.class)
                        );
                } catch (Exception e) {
                        return Optional.empty();
                }

                return Optional.of(roles);
        }

        public Optional<Rol> crear(Rol rol) {
                String sql = "EXEC " + Constantes.SP.SP_CAST_ROL + " @opcion = :opcion, @nombre = :nombre ";
                MapSqlParameterSource params = new MapSqlParameterSource();
                
                params.addValue("opcion", 2);
                params.addValue("nombre", rol.getNombre());

                try {
                        Long id = namedParameterJdbcTemplate.queryForObject(
                                sql, 
                                params, 
                                new ResultMapper()
                                );

                        rol.setId(id);
                } catch (Exception e) {
                        return Optional.empty();
                }
                
                return Optional.of(rol); 
        }
}
