package io.drift.core.config;

import io.drift.core.api.Flow;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DriftEngine {

	private List<Flow> flows = new ArrayList<Flow>();
	
	public DriftEngine() {

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