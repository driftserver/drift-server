package io.drift.jdbc.domain.session;

import io.drift.core.store.storage.Storable;

public class DataCaptureSession implements Storable {

	private SessionId id;

	protected DataCaptureSession() {
	}

	public DataCaptureSession(SessionId id) {
		this.id = id;
	}

	@Override
	public SessionId getId() {
		return id;
	}

}
