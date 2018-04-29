package ru.aizen.domain.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.attribute.Attribute;

import javax.sql.DataSource;

@Component
public class AttributeDao extends ShadowDao {
    private static JdbcTemplate staticTemplate;

    public AttributeDao(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
        staticTemplate = template;
    }

    public static Attribute getAttributeById(int id) {
        String sql = "SELECT\n" +
                "id,\n" +
                "name,\n" +
                "length\n" +
                "FROM tbl_attribute\n" +
                "WHERE id = ?";
        return staticTemplate.queryForObject(sql, new Object[]{id}, new AttributeRowMapper());
    }
}
