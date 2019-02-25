package io.drift.ui.app.page.workbench;

import io.drift.core.api.Flow;
import io.drift.core.ui.DriftServer;
import io.drift.core.ui.component.flow.FlowComponent;
import io.drift.ui.app.page.layout.MainLayout;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

@MountPath("workbench")
@WicketHomePage
public class WorkbenchPage extends MainLayout {

	private String selectedFlow;
	
	@SpringBean
	transient DriftServer driftServer;

	private WebMarkupContainer container;

	public WorkbenchPage() {
		container = new WebMarkupContainer("temp"){
			protected void onConfigure() {
				super.onConfigure();
				if (selectedFlow != null)
					renderActiveFlow();
			}
		};
		driftServer.getEngine().getFlows().forEach((flow) -> {
			addFlow(flow.getName());
		});
		container.setOutputMarkupId(true);
		container.add(new Label("active"));
		add(container);
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
		select.add(new FlowComponent("flow", flowName));

	}

	private void renderActiveFlow() {

		Flow flow = driftServer.getEngine().getFlows().stream().filter((f) -> {
			return f.getName().equals(selectedFlow);
		}).findFirst().get();

		container.replace(driftServer.createFlowComponent(flow, "active"));
	}

}
