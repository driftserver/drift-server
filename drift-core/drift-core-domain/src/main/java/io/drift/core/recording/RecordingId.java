package io.drift.core.recording;

import io.drift.core.store.storage.StorageId;

import java.io.Serializable;

public class RecordingId extends StorageId implements Serializable {

	protected RecordingId() {
	}

	public RecordingId(String id) {
		super(id);
	}



}
