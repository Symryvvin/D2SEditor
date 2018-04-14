package ru.aizen.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ShadowDao {
    protected final JdbcTemplate template;

    @Autowired
    public ShadowDao(@Qualifier("dataSource") DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }
}
