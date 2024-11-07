package com.ejercicio.inventario.castores.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ejercicio.inventario.castores.Entities.Rol;
import com.ejercicio.inventario.castores.Entities.Usuario;
public class UsuarioMapper implements RowMapper<Usuario> {
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setContraseña(rs.getString("contraseña"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setStatus(rs.getInt("status"));
        Rol rol = new Rol();
        rol.setId(rs.getLong("idRol"));
        usuario.setRol(rol);
        
        return usuario;
    }
}

