package io.drift.core.recording.model;

import io.drift.core.metamodel.id.ModelId;

import java.io.Serializable;

public class RecordingId extends ModelId implements Serializable {

	protected RecordingId() {
	}

	public RecordingId(String id) {
		super(id);
	}



}
