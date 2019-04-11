package io.drift.jdbc.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.shell.SpringShellAutoConfiguration;
import org.springframework.shell.jline.JLineShellAutoConfiguration;
import org.springframework.shell.standard.StandardAPIAutoConfiguration;
import org.springframework.shell.standard.commands.StandardCommandsAutoConfiguration;

import io.drift.jdbc.usecase.ConnectAction;
import io.drift.jdbc.usecase.TakeSnapshotAction;

@Configuration
@Import({ SpringShellAutoConfiguration.class, JLineShellAutoConfiguration.class, StandardAPIAutoConfiguration.class,
		StandardCommandsAutoConfiguration.class })
public class DriftJdbcCliConfig {

	@Autowired
	private TakeSnapshotAction snapshotAction;

	@Autowired
	private ConnectAction connectAction;

	@Bean
	public JdbcCommands jdbcCommands() {
		return new JdbcCommands(connectAction, snapshotAction);
	}
}
