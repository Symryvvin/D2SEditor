package ru.aizen.domain.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.aizen.domain.character.entity.Attribute;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttributeRowMapper implements RowMapper<Attribute> {

    @Override
    public Attribute mapRow(ResultSet rs, int i) throws SQLException {
        return new Attribute(rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("length"));
    }
}
