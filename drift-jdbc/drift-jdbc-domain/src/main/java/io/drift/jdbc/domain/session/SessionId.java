package io.drift.jdbc.domain.session;

import io.drift.core.store.storage.StorageId;

public class SessionId extends StorageId {

	protected SessionId() {
	}

	public SessionId(String id) {
		super(id);
	}

}
