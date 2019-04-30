package io.drift.jdbc.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.drift.core.store.ModelStoreException;

public class ConnectAction {

	Logger logger = LoggerFactory.getLogger(ConnectAction.class);

	private DBSnapshotFlow flow;

	public ConnectAction(DBSnapshotFlow flow) {
		this.flow = flow;
	}

	public void run() {
		try {
			flow.connect();
		} catch (ModelStoreException e) {
			logger.error("error running connect action", e);
		}
	}

}
