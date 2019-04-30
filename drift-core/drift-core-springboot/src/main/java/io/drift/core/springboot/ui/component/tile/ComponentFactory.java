package io.drift.core.springboot.ui.component.tile;

import org.apache.wicket.Component;

abstract public class ComponentFactory {

	abstract public Component create(String id);
	
}
