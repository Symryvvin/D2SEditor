package ru.aizen.domain.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.aizen.domain.character.entity.Waypoint;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WaypointDao extends ShadowDao {

    public WaypointDao(@Qualifier("dataSource") DataSource dataSource) {
        super(dataSource);
    }

    public Waypoint getWaypointByPosition(int position) {
        String sql = "SELECT name, act\n" +
                "FROM tbl_waypoint\n" +
                "WHERE position = ?";
        return template.queryForObject(sql, new Object[]{position}, new WaypointRowMapper());
    }

    private class WaypointRowMapper implements RowMapper<Waypoint> {

        @Override
        public Waypoint mapRow(ResultSet rs, int i) throws SQLException {
            return new Waypoint(rs.getInt("act"),
                    rs.getString("name"));
        }
    }
}
