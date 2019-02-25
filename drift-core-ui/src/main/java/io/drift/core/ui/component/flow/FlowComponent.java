package io.drift.core.ui.component.flow;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class FlowComponent extends Panel {

	public FlowComponent(String id, String label) {
		super(id);
		add(new Label("label", label));
	}

}
