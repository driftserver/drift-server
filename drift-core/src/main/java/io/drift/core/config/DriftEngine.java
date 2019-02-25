package io.drift.core.config;

import io.drift.core.api.Flow;

import java.util.ArrayList;
import java.util.List;

public class DriftEngine {

	private List<Flow> flows = new ArrayList<Flow>();
	
	public DriftEngine() {
		init();
	}

	private void init() {
	}

	public List<Flow> getFlows() {
		return flows;
	}

	public void addFlow(Flow flow){	
		flows.add(flow);	
	}
	
	public void registerPlugin(EnginePlugin plugin) {
		plugin.register(this);
	}

}	