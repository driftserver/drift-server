package io.drift.core.springboot;

import io.drift.core.api.Flow;
import io.drift.core.config.DriftEngine;
import io.drift.core.springboot.ui.component.tile.ComponentFactory;
import org.apache.wicket.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Component
public class DriftServer {
	
	private Map<Flow, ComponentFactory> factoryForFlow = new HashMap<Flow, ComponentFactory>();

	@Autowired
	private DriftEngine driftEngine;

	public DriftServer() {

	}

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
