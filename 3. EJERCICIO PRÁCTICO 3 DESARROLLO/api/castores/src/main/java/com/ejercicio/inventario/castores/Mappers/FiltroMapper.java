package com.ejercicio.inventario.castores.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ejercicio.inventario.castores.Entities.Filtro;

public class FiltroMapper<T> implements RowMapper<Filtro<T>>{
     @Override
    public Filtro<T> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Filtro<T> filtro = new Filtro<T>();
        filtro.setPaginas(rs.getInt("paginas"));
        filtro.setTotal(rs.getInt("total"));
        return filtro;
    }
}
