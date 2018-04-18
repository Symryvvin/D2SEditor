package ru.aizen.domain.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.aizen.domain.character.attribute.Skill;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillRowMapper implements RowMapper<Skill> {
    @Override
    public Skill mapRow(ResultSet rs, int i) throws SQLException {
        return new Skill(rs.getInt("byte"),
                rs.getString("name"),
                rs.getString("icon"),
                rs.getInt("page"),
                rs.getInt("row_number"),
                rs.getInt("col_number"));
    }
}
