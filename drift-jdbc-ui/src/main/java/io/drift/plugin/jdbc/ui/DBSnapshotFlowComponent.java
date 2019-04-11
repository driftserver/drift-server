package io.drift.plugin.jdbc.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;

public class DBSnapshotFlowComponent extends Panel {

	public DBSnapshotFlowComponent(String id) {
		super(id);
		add(new AjaxLink<Void>("takeSnapshot") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("clicked");
				
			}
		});
	}

}
