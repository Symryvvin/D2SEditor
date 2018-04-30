package ru.aizen.domain.dao;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.Character;
import ru.aizen.domain.character.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CharacterDao extends ShadowDao {

    public CharacterDao(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
    }

    public CharacterClass getCharacterClassByValue(int value) {
        String sql = "SELECT name FROM tbl_class WHERE id = " + value;
        return template.queryForObject(sql, new CharacterClassMapper());
    }

    public byte getValueByCharacterClass(CharacterClass characterClass) {
        String sql = "SELECT id FROM tbl_class WHERE UPPER(name) = '" + characterClass.name() + "'";
        return template.queryForObject(sql, Byte.class);
    }

    public ObservableList<Title> getTitleListByCharacterClassAndStatus(Character character) {
        Status status = character.getMetaBlock().getStatus();
        CharacterClass characterClass = character.getMetaBlock().getCharacterClass();
        String sql = "SELECT tbl_title.name AS name,\n" +
                "tbl_title.difficult AS difficult,\n" +
                "tbl_title.value AS value\n" +
                "FROM tbl_title\n" +
                "LEFT JOIN tbl_class ON tbl_class.gender = tbl_title.gender\n" +
                "WHERE tbl_title.is_expansion = '" + status.isExpansion() + "'\n" +
                "AND tbl_title.is_hardcore = '" + status.isHardcore() + "'\n" +
                "AND (UPPER(tbl_class.name) = '" + characterClass.name() + "' OR tbl_title.gender = '')\n" +
                "GROUP BY tbl_title.name\n" +
                "ORDER BY CASE tbl_title.difficult\n" +
                "WHEN 'start' THEN 0\n" +
                "WHEN 'normal' THEN 1\n" +
                "WHEN 'nightmare' THEN 2\n" +
                "WHEN 'hell' THEN 3 \n" +
                "END";
        return new ObservableListWrapper<>(template.query(sql, new TitleMapper()));
    }

    public Title getTitleByValue(CharacterClass characterClass, Status status, int value) {
        String sql = "SELECT t.name AS name,\n" +
                "t.difficult AS difficult,\n" +
                "t.value AS value\n" +
                "FROM tbl_title t\n" +
                "LEFT JOIN tbl_class c ON c.gender = t.gender\n" +
                "WHERE t.is_expansion = '" + status.isExpansion() + "'\n" +
                "AND t.is_hardcore = '" + status.isHardcore() + "'\n" +
                "AND (UPPER(c.name) = '" + characterClass.name() + "' OR t.gender = '')\n" +
                "AND t.value = " + value;
        return template.queryForObject(sql, new TitleMapper());
    }

    public Difficult getCurrentDifficult(Character character) {
        int value = character.getMetaBlock().getTitle().getValue();
        Status status = character.getMetaBlock().getStatus();
        CharacterClass characterClass = character.getMetaBlock().getCharacterClass();
        String sql = "SELECT DISTINCT UPPER(tbl_title.difficult) AS difficult\n" +
                "FROM tbl_title\n" +
                "LEFT JOIN tbl_class ON tbl_class.gender = tbl_title.gender\n" +
                "WHERE value = " + value + "\n" +
                "AND  tbl_title.is_expansion = '" + status.isExpansion() + "'\n" +
                "AND tbl_title.is_hardcore = '" + status.isHardcore() + "'\n" +
                "AND (UPPER(tbl_class.name) = '" + characterClass.name() + "' OR tbl_title.gender = '')";
        return template.queryForObject(sql, Difficult.class);
    }

    public byte getTitleValue(Status status, Difficult difficult) {
        String sql = "SELECT tbl_title.value AS value\n" +
                "FROM tbl_title\n" +
                "WHERE tbl_title.is_expansion = '" + status.isExpansion() + "'\n" +
                "AND tbl_title.is_hardcore = '" + status.isHardcore() + "'\n" +
                "AND tbl_title.difficult = '" + difficult.name().toLowerCase() + "'\n" +
                "LIMIT 1";
        return template.queryForObject(sql, Byte.class);
    }


    public class CharacterClassMapper implements RowMapper<CharacterClass> {

        @Override
        public CharacterClass mapRow(ResultSet resultSet, int i) throws SQLException {
            return CharacterClass.valueOf(resultSet.getString("name").toUpperCase());
        }
    }

    public class TitleMapper implements RowMapper<Title> {

        @Override
        public Title mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Title(resultSet.getString("name"),
                    resultSet.getString("difficult"),
                    resultSet.getInt("value"));
        }
    }
}
