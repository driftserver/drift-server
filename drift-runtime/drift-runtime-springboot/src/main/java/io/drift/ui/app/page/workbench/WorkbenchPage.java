package io.drift.ui.app.page.workbench;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import io.drift.core.api.Flow;
import io.drift.core.springboot.DriftServer;
import io.drift.core.springboot.ui.component.tile.TileComponent;
import io.drift.ui.app.page.layout.MainLayout;

@MountPath("workbench")
@WicketHomePage
public class WorkbenchPage extends MainLayout {

	private WebMarkupContainer container;

	@SpringBean
	DriftServer driftServer;


	private String selectedFlow;

	public WorkbenchPage() {

		//add(container =listView("flows", driftServer.getEngine().getFlows(), item -> {
			 //item.add(new Label("name", item.getModelObject().getName()));
			//addFlow(tile.getId());
		//}));
	}

	private void addFlow(String flowName) {
		AjaxLink<Void> select = new AjaxLink<Void>("select") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				selectedFlow = flowName;
				target.add(container);
			}
		};
		container.add(select);
		select.add(new TileComponent("tile", flowName));

	}

	private void renderActiveFlow() {

		Flow flow = driftServer.getEngine().getFlows().stream().filter((f) -> {
			return f.getName().equals(selectedFlow);
		}).findFirst().get();

		container.replace(driftServer.createFlowComponent(flow, "active"));
	}

}
