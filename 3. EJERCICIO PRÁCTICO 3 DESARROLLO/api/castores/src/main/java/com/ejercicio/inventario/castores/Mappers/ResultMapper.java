package com.ejercicio.inventario.castores.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResultMapper implements RowMapper<Long>{
    @Override
   public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long result = Long.valueOf(0);
        result = rs.getLong("Return Value");
        return result;
   }
}
