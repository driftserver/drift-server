package com.github.driftserver.core.recording.model;

import com.github.driftserver.core.metamodel.id.ModelId;

import java.io.Serializable;

public class RecordingId extends ModelId implements Serializable {

	protected RecordingId() {
	}

	public RecordingId(String id) {
		super(id);
	}



}
