package com.github.driftserver.core.recording.model;

import com.github.driftserver.core.metamodel.Model;
import com.github.driftserver.core.system.EnvironmentKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recording implements Model {

	private Map<String, SubSystemDescription> subSystemDescriptions = new HashMap<>();

	private SystemState initialState, finalstate;

	private List<RecordingStep> steps = new ArrayList<>();

	private RecordingId id;

	private EnvironmentKey environmentKey;

	private String name;

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

	public RecordingId getId() {
		return id;
	}

    public void addSubSystemDescription(String subSystemName, SubSystemDescription subSystemDescription) {
		subSystemDescriptions.put(subSystemName, subSystemDescription);
    }

    public SubSystemDescription getSubSystemDescription(String subSystemName) {
		return subSystemDescriptions.get(subSystemName);
	}

    public void setEnvironmentKey(EnvironmentKey environmentKey) {
        this.environmentKey = environmentKey;
    }

    public EnvironmentKey getEnvironmentKey() {
        return environmentKey;
    }

	public SystemState getInitialState() {
		return initialState;
	}

	public void setInitialState(SystemState initialState) {
		this.initialState = initialState;
	}

	public SystemState getFinalstate() {
		return finalstate;
	}

	public void setFinalstate(SystemState finalstate) {
		this.finalstate = finalstate;
	}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
