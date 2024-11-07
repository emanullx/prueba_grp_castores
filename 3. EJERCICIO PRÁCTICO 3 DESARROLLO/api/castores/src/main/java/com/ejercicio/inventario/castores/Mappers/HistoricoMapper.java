package com.ejercicio.inventario.castores.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ejercicio.inventario.castores.Entities.Historico;
import com.ejercicio.inventario.castores.Entities.Producto;
import com.ejercicio.inventario.castores.Entities.Usuario;

public class HistoricoMapper implements RowMapper<Historico> {
    @Override
    public Historico mapRow(ResultSet rs, int rowNum) throws SQLException {
        Historico historico = new Historico();
        historico.setId(rs.getLong("id"));
        historico.setUbicacion(rs.getString("ubicacion"));
        historico.setStockAntes(rs.getInt("stockAntes"));
        historico.setStockDespues(rs.getInt("stockDespues"));
        historico.setFechaMovimiento(rs.getDate("fechaMovimiento"));
        historico.setTipoMovimiento(rs.getInt("tipoMovimiento"));
        historico.setMovimiento(rs.getInt("movimiento"));
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("idUsuario"));
        usuario.setNombre(rs.getString("nombreUsuario"));
        historico.setUsuario(usuario);
        Producto producto = new Producto();
        producto.setId(rs.getLong("idProducto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setUrlImagen(rs.getString("urlImagen"));
        historico.setProducto(producto);
        return historico;
    }
}
