package com.ejercicio.inventario.castores.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ejercicio.inventario.castores.Entities.Constantes;
import com.ejercicio.inventario.castores.Entities.Filtro;
import com.ejercicio.inventario.castores.Entities.Producto;
import com.ejercicio.inventario.castores.Mappers.FiltroMapper;
import com.ejercicio.inventario.castores.Mappers.ResultMapper;

@Service
public class ProductoRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Producto> crear(Producto producto, Long idUsuario, String ubicacion) {
                String sql = "EXEC " + Constantes.SP.SP_CAST_PRODUCTO + " @opcion = :opcion, @nombre = :nombre, @descripcion = :descripcion, @precio = :precio, @ubicacion = :ubicacion, @urlImagen = :urlImagen, @idUsuario = :idUsuario ";
                MapSqlParameterSource params = new MapSqlParameterSource();
                
                params.addValue("opcion", 3);
                params.addValue("nombre", producto.getNombre());
                params.addValue("descripcion", producto.getDescripcion());
                params.addValue("precio", producto.getPrecio());
                params.addValue("ubicacion", ubicacion);
                params.addValue("urlImagen", producto.getUrlImagen());
                params.addValue("idUsuario", idUsuario);

                try {
                    Long id = namedParameterJdbcTemplate.queryForObject(
                            sql, 
                            params, 
                            new ResultMapper()
                        );

                    producto.setId(id);
                } catch (Exception e) {
                    return Optional.empty();
                }
                
                return Optional.of(producto); 
    }
    public Optional<List<Producto>> obtener(Filtro<Producto> filtro) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_PRODUCTO + " @opcion = :opcion, @nombre = :nombre, @descripcion = :descripcion, @next = :next, @offset = :offset ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<Producto> productos = List.of(); 
        Producto producto = filtro.getDatos().orElse(new Producto()); 

        params.addValue("opcion", 2);
        params.addValue("nombre", Optional.of(producto.getNombre()).orElse(null));
        params.addValue("descripcion", Optional.of(producto.getDescripcion()).orElse(null));
        params.addValue("next", filtro.getNext());
        params.addValue("offset", filtro.getOffSet());
        try {
            productos = namedParameterJdbcTemplate.query(
                    sql, 
                    params, 
                    new BeanPropertyRowMapper<>( Producto.class)         
            );

        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(productos);
    }

    public Optional<Filtro<Producto>> obtenerFiltroListado(Filtro<Producto> filtro) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_PRODUCTO + " @opcion = :opcion, @nombre = :nombre, @descripcion = :descripcion, @maxRegPag = :maxRegPag ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        Producto producto = filtro.getDatos().orElse(new Producto()); 
        params.addValue("opcion", 1);
        params.addValue("nombre", Optional.of(producto.getNombre()).orElse(null));
        params.addValue("descripcion", Optional.of(producto.getDescripcion()).orElse(null));
        params.addValue("maxRegPag", filtro.getMaxRegPagina());
        try {
            filtro = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new FiltroMapper<Producto>()          
            );

            if(filtro != null)
                filtro.setDatos(Optional.of(producto));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(filtro);
    }
}
