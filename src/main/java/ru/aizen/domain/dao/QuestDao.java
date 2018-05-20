package ru.aizen.domain.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.entity.Act;
import ru.aizen.domain.character.entity.Quest;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuestDao extends ShadowDao {
    public QuestDao(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
    }

    public Quest getQuestByPosition(int position, Act act) {
        int actValue = act.ordinal() + 1;
        String sql = "SELECT name, \n" +
                "act,\n" +
                "quest_position,\n" +
                "position,\n" +
                "icon_active,\n" +
                "icon_complete\n" +
                "FROM tbl_quest\n" +
                "WHERE position = ?" +
                "AND act = ?";
        return template.queryForObject(sql, new Object[]{position, actValue}, new QuestRowMapper());
    }

    private class QuestRowMapper implements RowMapper<Quest> {

        @Override
        public Quest mapRow(ResultSet rs, int i) throws SQLException {
            return new Quest(rs.getInt("act"),
                    rs.getString("name"),
                    rs.getString("quest_position"),
                    rs.getString("position"),
                    rs.getString("icon_active"),
                    rs.getString("icon_complete"));
        }
    }

}
