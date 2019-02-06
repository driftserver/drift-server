package io.drift.ui.app.page.workbench;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import io.drift.core.config.DriftEngine;
import io.drift.ui.app.page.layout.MainLayout;

@MountPath("workbench")
public class WorkbenchPage extends MainLayout {

	@SpringBean
	DriftEngine driftEngine;

	public WorkbenchPage() {
		System.out.println("flows: ");
		driftEngine.getFlows().forEach((flow) -> {
			System.out.println(flow.getName());
		});
	}

}
