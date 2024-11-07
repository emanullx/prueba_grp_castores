package com.ejercicio.inventario.castores.Repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.ejercicio.inventario.castores.Entities.Constantes;
import com.ejercicio.inventario.castores.Entities.Usuario;
import com.ejercicio.inventario.castores.Mappers.ResultMapper;
import com.ejercicio.inventario.castores.Mappers.UsuarioMapper;

@Service
public class UsuarioRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Usuario> obtener(Optional<Long> id, Optional<String> nombre, Optional<String> correo, Optional<Integer> status) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_USUARIO + " :opcion, :id, :nombre, :correo, :status ";
        Usuario usuario = new Usuario();
        MapSqlParameterSource params = new MapSqlParameterSource();
         
        params.addValue("opcion", 1);
        params.addValue("id", id.orElse(null));
        params.addValue("nombre", nombre.orElse(null));
        params.addValue("correo", correo.orElse(null));
        params.addValue("status", status.orElse(null));
        try {
            usuario = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new UsuarioMapper()
                );
        } catch (Exception e) {
            return Optional.empty();
        }
        
        return Optional.of(usuario); 
    }

    public Optional<List<Usuario>> obtenerListado(Optional<Integer> status) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_USUARIO + " @opcion = :opcion, @status = :status ";
        List<Usuario> usuarios = List.of();
        MapSqlParameterSource params = new MapSqlParameterSource();
         
        params.addValue("opcion", 1);
        params.addValue("status", status.orElse(null));
        try {
            usuarios = namedParameterJdbcTemplate.query(
                    sql, 
                    params, 
                    new UsuarioMapper()
                );
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(usuarios);
    }

    public Optional<Usuario> crear(Usuario usuario) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_USUARIO + " @opcion = :opcion, @nombre = :nombre, @correo = :correo, @idRol = :idRol ";
        MapSqlParameterSource params = new MapSqlParameterSource();
         
        params.addValue("opcion", 2);
        params.addValue("nombre", usuario.getNombre());
        params.addValue("correo", usuario.getCorreo());
        params.addValue("idRol", usuario.getRol().getId());

        try {
            Long id = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new ResultMapper()
                );

            usuario.setId(id);
        } catch (Exception e) {
            return Optional.empty();
        }
        
        return Optional.of(usuario); 
    }
}
