package com.ejercicio.inventario.castores.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ejercicio.inventario.castores.Entities.Permiso;
import com.ejercicio.inventario.castores.Entities.PermisoXRol;
import com.ejercicio.inventario.castores.Entities.Rol;

public class PermisoXRolMapper implements RowMapper<PermisoXRol> {
    @Override
    public PermisoXRol mapRow(ResultSet rs, int rowNum) throws SQLException {
        PermisoXRol permisoXRol = new PermisoXRol();
        permisoXRol.setId(rs.getLong("id"));
        Rol rol = new Rol();
        rol.setId(rs.getLong("idRol"));
        permisoXRol.setRol(rol);
        Permiso permiso = new Permiso();
        permiso.setClave(rs.getString("clave"));
        permiso.setId(rs.getLong("idPermiso"));
        permiso.setDescripcion(rs.getString("descripcion"));
        permisoXRol.setPermiso(permiso);
        return permisoXRol;
    }
}
