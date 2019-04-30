package io.drift.jdbc.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.drift.core.store.ModelStoreException;
import io.drift.jdbc.domain.session.SessionId;

public class TakeSnapshotAction {

	private DBSnapshotFlow flow;

	Logger logger = LoggerFactory.getLogger(TakeSnapshotAction.class);
	private SessionId sessionId;

	public TakeSnapshotAction(DBSnapshotFlow flow, SessionId sessionId) {
		this.flow = flow;
		this.sessionId = sessionId;
	}

	public void run() {
		try {
			flow.takeSnaphot(sessionId);
			;
		} catch (ModelStoreException e) {
			logger.error("error running snapshot action", e);
		}
	}

}
