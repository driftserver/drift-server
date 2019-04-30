package io.drift.core.springboot.ui.component.tile;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class TileComponent extends Panel {

	public TileComponent(String id, String label) {
		super(id);
		add(new Label("label", label));
	}

}
