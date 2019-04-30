package io.drift.core.springboot;

import io.drift.core.config.DriftEngine;
import io.drift.core.config.EnginePlugin;
import io.drift.core.springboot.DriftServer;

abstract public class DriftServerPlugin extends EnginePlugin {

	public void register(DriftEngine engine) {
	}

	public void registerUI(DriftServer driftServer) {
	}

}
