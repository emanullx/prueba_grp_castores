package com.ejercicio.inventario.castores.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ejercicio.inventario.castores.Entities.Inventario;
import com.ejercicio.inventario.castores.Entities.Producto;

public class InventarioMapper implements RowMapper<Inventario> {
    @Override
    public Inventario mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inventario inventario = new Inventario();
        inventario.setId(rs.getLong("id"));
        inventario.setStatus(rs.getInt("status"));
        inventario.setStock(rs.getInt("stock"));
        inventario.setUbicacion(rs.getString("ubicacion"));

        Producto producto = new Producto();
        producto.setId(rs.getLong("idProducto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setUrlImagen(rs.getString("urlImagen"));
        producto.setPrecio(rs.getDouble("precio"));
        inventario.setProducto(producto);
        return inventario;
    }
}
