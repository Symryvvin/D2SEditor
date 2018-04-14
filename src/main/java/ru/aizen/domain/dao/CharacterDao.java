package ru.aizen.domain.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.CharacterClass;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CharacterDao extends ShadowDao {

    public CharacterDao(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
    }

    public CharacterClass getCharacterClassByValue(int value) {
        String sql = "SELECT name FROM lod.tbl_class WHERE id = " + value;
        return template.queryForObject(sql, new CharacterClassMapper());
    }

    public byte getValueByCharacterClass(CharacterClass characterClass) {
        String sql = "SELECT id FROM lod.tbl_class WHERE UPPER(name) = '" + characterClass.name() + "'";
        return template.queryForObject(sql, Byte.class);
    }


    private class CharacterClassMapper implements RowMapper<CharacterClass> {

        @Override
        public CharacterClass mapRow(ResultSet resultSet, int i) throws SQLException {
            return CharacterClass.valueOf(resultSet.getString("name").toUpperCase());
        }
    }
}
