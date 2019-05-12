package io.drift.jdbc.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.drift.core.store.ModelStoreException;
import io.drift.core.recording.RecordingId;

public class TakeSnapshotAction {

	private DBSnapshotFlow flow;

	Logger logger = LoggerFactory.getLogger(TakeSnapshotAction.class);
	private RecordingId sessionId;

	public TakeSnapshotAction(DBSnapshotFlow flow, RecordingId sessionId) {
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
