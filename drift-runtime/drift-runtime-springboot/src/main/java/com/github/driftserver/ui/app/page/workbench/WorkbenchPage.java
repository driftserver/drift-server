package com.github.driftserver.ui.app.page.workbench;

import com.github.driftserver.ui.app.page.recordings.RecordingsPage;
import com.github.driftserver.ui.app.page.layout.MainLayout;
import com.github.driftserver.ui.app.page.system.SystemDescriptionPage;
import org.apache.wicket.markup.html.panel.Fragment;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.github.driftserver.ui.infra.WicketUtil.*;

@MountPath("/")
public class WorkbenchPage extends MainLayout {

	class WorkbenchCardProps implements Serializable {
		String title;
		String description;
		Class page;
	}

	class WorkflowCardFragment extends Fragment {

		public WorkflowCardFragment(String id, WorkbenchCardProps cardProps) {
			super(id, "workflowFragment", WorkbenchPage.this);
			add(label("title", cardProps.title));
			add(label("description", cardProps.description));
			add(ajaxLink("select", (target)-> setResponsePage(cardProps.page)));
		}

	}


	public WorkbenchPage() {

		List<WorkbenchCardProps> workflows = new ArrayList<>();
		workflows.add(new WorkbenchCardProps(){{
			title = "Recordings";
			description = "manage recordings";
			page = RecordingsPage.class;
		}});
		workflows.add(new WorkbenchCardProps() {{
			title = "System";
			description = "Connection details by subsystem and environment, (sub)system partitioning";
			page = SystemDescriptionPage.class;
		}});

		add(listView("workflows", workflows, (workflowItem)-> {
			workflowItem.add(new WorkflowCardFragment("workflow", workflowItem.getModelObject()));
		}));

	}

}
