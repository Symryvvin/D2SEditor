package ru.aizen.domain.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.attribute.Attribute;

import javax.sql.DataSource;

@Component
public class AttributeDao extends ShadowDao {

    public AttributeDao(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
    }

    public Attribute getAttributeById(int id) {
        String sql = "SELECT\n" +
                "id,\n" +
                "name,\n" +
                "length\n" +
                "FROM tbl_attribute\n" +
                "WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, new AttributeRowMapper());
    }

    public String getExperienceAtLevel(int level) {
        String sql = "SELECT experience\n" +
                "FROM tbl_level\n" +
                "WHERE level = ?";
        return String.valueOf(template.queryForObject(sql, new Object[]{level}, Long.class));
    }

    public Long getMaxGoldValueAtLevel(int level) {
        String sql = "SELECT gold_inv\n" +
                "FROM tbl_level\n" +
                "WHERE level = ?";
        return template.queryForObject(sql, new Object[]{level}, Long.class);
    }

    public Long getMaxGoldBankValueAtLevel(int level) {
        String sql = "SELECT gold_stash\n" +
                "FROM tbl_level\n" +
                "WHERE level = ?";
        return template.queryForObject(sql, new Object[]{level}, Long.class);
    }
}
