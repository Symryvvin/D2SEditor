package ru.aizen.domain.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.CharacterClass;
import ru.aizen.domain.character.attribute.Skill;

import javax.sql.DataSource;
import java.util.List;

@Component
public class SkillDao extends ShadowDao {

    public SkillDao(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
    }

    public List<Skill> getSkills(CharacterClass characterClass) {
        String sql = "SELECT s.byte_order AS byte,\n" +
                "  s.name AS name,\n" +
                "  s.page AS page,\n" +
                "  s.row_number AS row_number,\n" +
                "  s.col_number AS col_number,\n" +
                "  s.icon AS icon\n" +
                "FROM tbl_skill s\n" +
                "  JOIN tbl_class c ON s.class_id = c.id\n" +
                "WHERE UPPER(c.name) = ?\n" +
                "ORDER BY s.byte_order";
        return template.query(sql, new Object[]{characterClass.name()}, new SkillRowMapper());
    }

    public String getSkillPage(CharacterClass characterClass, int number) {
        String sql = "SELECT p.name AS name\n" +
                "FROM tbl_skill_page p\n" +
                "JOIN tbl_class c ON p.class_id = c.id\n" +
                "WHERE UPPER(c.name) = ?\n" +
                "AND p.page_order = ?;";
        return template.queryForObject(sql, new Object[]{characterClass.name(), number}, String.class);
    }
}
