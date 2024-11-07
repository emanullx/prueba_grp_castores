package com.ejercicio.inventario.castores.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ejercicio.inventario.castores.Entities.Constantes;
import com.ejercicio.inventario.castores.Entities.Filtro;
import com.ejercicio.inventario.castores.Entities.Historico;
import com.ejercicio.inventario.castores.Mappers.FiltroMapper;
import com.ejercicio.inventario.castores.Mappers.HistoricoMapper;

@Service
public class HistoricoRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

     public Optional<List<Historico>> obtener(Filtro<Historico> filtro) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_HISTORICO + " @opcion = :opcion, @idProducto = :idProducto, @idUsuario = :idUsuario, @ubicacion = :ubicacion, @next = :next, @offset = :offset ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<Historico> historicos = List.of(); 
        Historico historico = filtro.getDatos().orElse(new Historico()); 
        try {
           
            params.addValue("opcion", 2);
            params.addValue("idProducto", historico.getProducto().getId().equals(Long.valueOf(0)) ? null : historico.getProducto().getId());
            params.addValue("idUsuario", historico.getUsuario().getId().equals(Long.valueOf(0)) ? null : historico.getUsuario().getId() );
            params.addValue("ubicacion", historico.getUbicacion().equals("") ? null : historico.getUbicacion());
            params.addValue("next", filtro.getNext());
            params.addValue("offset", filtro.getOffSet());

            historicos = namedParameterJdbcTemplate.query(
                    sql, 
                    params, 
                    new HistoricoMapper()        
            );

        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(historicos);
    }

    public Optional<Filtro<Historico>> obtenerFiltroListado(Filtro<Historico> filtro) {
        String sql = "EXEC " + Constantes.SP.SP_CAST_HISTORICO + " @opcion = :opcion, @idProducto = :idProducto, @idUsuario = :idUsuario, @ubicacion = :ubicacion, @maxRegPag = :maxRegPag ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        Historico historico = filtro.getDatos().orElse(new Historico()); 
        try {
            params.addValue("opcion", 1);
            params.addValue("idProducto", historico.getProducto().getId().equals(Long.valueOf(0)) ? null : historico.getProducto().getId());
            params.addValue("idUsuario", historico.getUsuario().getId().equals(Long.valueOf(0)) ? null : historico.getUsuario().getId() );
            params.addValue("ubicacion", historico.getUbicacion().equals("") ? null : historico.getUbicacion());
            params.addValue("maxRegPag", filtro.getMaxRegPagina());

            filtro = namedParameterJdbcTemplate.queryForObject(
                    sql, 
                    params, 
                    new FiltroMapper<Historico>()          
            );

            if(filtro != null)
                filtro.setDatos(Optional.of(historico));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(filtro);
    }
}
