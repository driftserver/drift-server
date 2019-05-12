package io.drift.core.recording;

import io.drift.core.store.storage.Storable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recording implements Storable {

	private Map<String, SubSystemDescription> subSystemDescriptions = new HashMap<>();

	private List<RecordingStep> steps = new ArrayList<>();

	private RecordingId id;

	protected Recording() {
	}

	public List<RecordingStep> getSteps() {
		return steps;
	}

	public Recording(RecordingId id) {
		this.id = id;
	}

	public void addStep(RecordingStep recordingStep) {
		steps.add(recordingStep);
	}

	@Override
	public RecordingId getId() {
		return id;
	}

    public void addSubSystemDescription(String subSystemName, SubSystemDescription subSystemDescription) {
		subSystemDescriptions.put(subSystemName, subSystemDescription);
    }

    public SubSystemDescription getSubSystemDescription(String subSystemName) {
		return subSystemDescriptions.get(subSystemName);
	}
}
