package com.ejercicio.inventario.castores.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ejercicio.inventario.castores.Entities.Constantes;
import com.ejercicio.inventario.castores.Entities.Filtro;
import com.ejercicio.inventario.castores.Entities.Inventario;
import com.ejercicio.inventario.castores.Mappers.FiltroMapper;
import com.ejercicio.inventario.castores.Mappers.InventarioMapper;
import com.ejercicio.inventario.castores.Mappers.ResultMapper;

@Service
public class InventarioRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

   public Optional<List<Inventario>> obtener(Filtro<Inventario> filtro) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_INVENTARIO + " @opcion = :opcion, @idProducto = :idProducto, @status = :status, @ubicacion = :ubicacion, @next = :next, @offset = :offset ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<Inventario> inventarios = List.of(); 
        Inventario inventario = filtro.getDatos().orElse(new Inventario()); 
        try {

            params.addValue("opcion", 2);
            params.addValue("idProducto", inventario.getProducto().getId().equals(Long.valueOf(0)) ? null : inventario.getProducto().getId().equals(""));
            params.addValue("status", inventario.getStatus() < 0 ? null : inventario.getStatus());
            params.addValue("ubicacion", inventario.getUbicacion().equals("") ? null : inventario.getUbicacion());
            params.addValue("next", filtro.getNext());
            params.addValue("offset", filtro.getOffSet());

            inventarios = namedParameterJdbcTemplate.query(
                    sql, 
                    params, 
                    new InventarioMapper()        
            );

        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(inventarios);
    }

    public Optional<Filtro<Inventario>> obtenerFiltroListado(Filtro<Inventario> filtro) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_INVENTARIO + " @opcion = :opcion, @idProducto = :idProducto, @status = :status, @ubicacion = :ubicacion, @maxRegPag = :maxRegPag ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        Inventario inventario = filtro.getDatos().orElse(new Inventario()); 
        try {
            params.addValue("opcion", 1);
            params.addValue("idProducto", inventario.getProducto().getId().equals(Long.valueOf(0)) ? null : inventario.getProducto().getId().equals(""));
            params.addValue("status", inventario.getStatus() < 0 ? null : inventario.getStatus());
            params.addValue("ubicacion", inventario.getUbicacion().equals("") ? null : inventario.getUbicacion());
            params.addValue("maxRegPag", filtro.getMaxRegPagina());

            filtro = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new FiltroMapper<Inventario>()          
            );

            if(filtro != null)
                filtro.setDatos(Optional.of(inventario));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(filtro);
    }

    public Optional<Inventario> actualizar(Inventario inventario, Long idUsuario, Long idProducto) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_INVENTARIO + " @opcion = :opcion, @id = :id, @idProducto = :idProducto, @stock = :stock, @ubicacion = :ubicacion, @idUsuario = :idUsuario ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        
        params.addValue("opcion", 4);
        params.addValue("id", inventario.getId());
        params.addValue("ubicacion", inventario.getUbicacion());
        params.addValue("stock", inventario.getStock());
        params.addValue("idUsuario", idUsuario);
        params.addValue("idProducto", idProducto);
        try {
            Long id = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new ResultMapper()
                );

            inventario.setId(id);
        } catch (Exception e) {
            return Optional.empty();
        }
        
        return Optional.of(inventario); 
    }
}