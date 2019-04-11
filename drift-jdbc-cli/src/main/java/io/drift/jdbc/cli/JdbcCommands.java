package io.drift.jdbc.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import io.drift.core.api.FocalArea;
import io.drift.jdbc.usecase.ConnectAction;
import io.drift.jdbc.usecase.TakeSnapshotAction;

@ShellComponent
public class JdbcCommands {

	private ConnectAction connectAction;

	@Autowired
	private FocalArea focalArea;
	private TakeSnapshotAction snapshotAction;

	public JdbcCommands(ConnectAction connectAction, TakeSnapshotAction snapshotAction) {
		this.connectAction = connectAction;
		this.snapshotAction = snapshotAction;
	}

	@ShellMethod("connect")
	public void connect() {
		System.out.println("!!! " + focalArea);
		connectAction.run();
	}

	@ShellMethod("snapshot")
	public void snapshot() {
		snapshotAction.run();
	}

}