package io.drift.core.ui;

import io.drift.core.api.Flow;
import io.drift.core.config.DriftEngine;
import io.drift.core.ui.component.flow.ComponentFactory;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;

public class DriftServer {
	
	private Map<Flow, ComponentFactory> factoryForFlow = new HashMap<Flow, ComponentFactory>();
	
	private DriftEngine driftEngine = new DriftEngine();

	public DriftEngine getEngine() {
		return driftEngine;
	}
	
	public Component createFlowComponent(Flow flow, String id) {
		return factoryForFlow.get(flow).create(id);
	}
	
	public void addComponentFactoryForFlow(Flow flow, ComponentFactory componentFactory) {
		factoryForFlow.put(flow, componentFactory);
	}
	
	public void registerPlugin(DriftServerPlugin plugin) {
		plugin.registerUI(this);
	}

}
