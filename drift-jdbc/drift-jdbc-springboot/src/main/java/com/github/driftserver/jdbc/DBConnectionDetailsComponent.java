package com.github.driftserver.jdbc;

import com.github.driftserver.jdbc.domain.system.JDBCConnectionDetails;
import org.apache.wicket.markup.html.panel.Panel;

import static com.github.driftserver.ui.infra.WicketUtil.label;

public class DBConnectionDetailsComponent extends Panel {

    public DBConnectionDetailsComponent(String id, JDBCConnectionDetails jdbcConnectionDetails) {
        super(id);
        add(label("jdbcUrl", jdbcConnectionDetails.getJdbcUrl()));
        add(label("userName", jdbcConnectionDetails.getUserName()));
        add(label("password", jdbcConnectionDetails.getPassword()));

    }
}
