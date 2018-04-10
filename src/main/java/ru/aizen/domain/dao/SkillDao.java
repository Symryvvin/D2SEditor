package ru.aizen.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.attribute.Skill;

import javax.sql.DataSource;
import java.util.List;

@Component
public class SkillDao {
    private final JdbcTemplate template;

    @Autowired
    public SkillDao(@Qualifier("dataSource") DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<Skill> getSkills(CharacterClass characterClass) {
        String sql = "SELECT s.byte_order AS byte,\n" +
                "  s.name AS name,\n" +
                "  s.page AS page,\n" +
                "  s.skill_row AS s_row,\n" +
                "  s.skill_col AS s_col,\n" +
                "  s.icon AS icon\n" +
                "FROM lod.tbl_skill s\n" +
                "  JOIN lod.tbl_class c ON s.class_id = c.id\n" +
                "WHERE c.name = ?\n" +
                "ORDER BY s.byte_order";
        return template.query(sql, new Object[]{characterClass.getName()}, new SkillRowMapper());
    }
}
