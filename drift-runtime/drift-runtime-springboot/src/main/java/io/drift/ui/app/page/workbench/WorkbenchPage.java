package io.drift.ui.app.page.workbench;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.app.page.recordings.RecordingsPage;
import org.apache.wicket.markup.html.panel.Fragment;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.ArrayList;
import java.util.List;

import static io.drift.ui.infra.WicketUtil.*;

@MountPath("/")
@WicketHomePage
public class WorkbenchPage extends MainLayout {

	class WorkflowCardFragment extends Fragment {

		public WorkflowCardFragment(String id, String name) {
			super(id, "workflowFragment", WorkbenchPage.this);
			add(label("title", name));
			add(label("description", name));
			add(ajaxLink("select", (target)-> { setResponsePage(RecordingsPage.class); }));
		}

	}


	public WorkbenchPage() {

		List<String> workflows = new ArrayList<>();
		workflows.add("recordings");

		add(listView("workflows", workflows, (workflowItem)-> {
			workflowItem.add(new WorkflowCardFragment("workflow", workflowItem.getModelObject()));
		}));

	}

}
