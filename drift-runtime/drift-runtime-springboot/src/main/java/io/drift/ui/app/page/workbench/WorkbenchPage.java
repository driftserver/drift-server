package io.drift.ui.app.page.workbench;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.app.page.layout.MainLayout2;
import io.drift.ui.app.page.recordings.RecordingsPage;
import io.drift.ui.app.page.system.SystemPage;
import org.apache.wicket.markup.html.panel.Fragment;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static io.drift.ui.infra.WicketUtil.*;

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
			page = SystemPage.class;
		}});

		add(listView("workflows", workflows, (workflowItem)-> {
			workflowItem.add(new WorkflowCardFragment("workflow", workflowItem.getModelObject()));
		}));

	}

}
