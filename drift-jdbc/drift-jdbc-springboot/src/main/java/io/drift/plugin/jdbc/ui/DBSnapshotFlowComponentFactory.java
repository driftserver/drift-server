package io.drift.plugin.jdbc.ui;

import io.drift.core.springboot.ui.component.tile.ComponentFactory;

import org.apache.wicket.Component;

public class DBSnapshotFlowComponentFactory extends ComponentFactory{

	@Override
	public Component create(String id) {
		return new DBSnapshotFlowComponent(id);
	}

}
