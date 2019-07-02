package io.drift.ui.app.page.system;

import io.drift.core.system.EnvironmentKey;
import io.drift.ui.app.component.stacktrace.ProblemDescriptionListComponent;
import io.drift.ui.app.flux.systemdescription.SystemActions;
import io.drift.ui.app.flux.systemdescription.SystemStore;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.config.WicketComponentRegistry;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.UUID;

import static io.drift.ui.infra.WicketUtil.*;

@MountPath("system/connections")
public class SystemConnectionsPage extends MainLayout {

    @SpringBean
    SystemStore systemStore;

    @SpringBean
    SystemActions systemActions;

    @SpringBean
    private WicketComponentRegistry wicketComponentRegistry;

    public SystemConnectionsPage() {
        add(new SystemConnectionsFragment("systemConnections"));
    }

    private class SystemConnectionsFragment extends Fragment{

        String selectedEnvironment;

        public SystemConnectionsFragment(String id) {
            super(id, "systemConnectionsFragment", SystemConnectionsPage.this);

            selectedEnvironment = systemStore.getEnvironments().get(0).getKey();

            Link test = ajaxLink("testConnectionSettings", (target-> {
                actionId = systemActions.testConnectivity(new EnvironmentKey(selectedEnvironment));
                refresh(target);
            }));
            test.add(label("envKey", ()-> selectedEnvironment));

            add(test);
            add(new ActionResultFragment("actionResult"));


            add(listView("environments", () -> systemStore.getEnvironments(), (item) -> {
                String environment = item.getModelObject().getKey();

                Link select = ajaxLink("select", (target) -> {
                    selectedEnvironment = environment;
                    actionId = null;
                    refresh(target);
                });
                addClass(select, environment.equals(selectedEnvironment) ? "active" : "text-primary");

                item.add(select);
                select.add(label("key", environment));
            }));

            add(new EnvironmentSettingsFragment("environmentSettings", () -> selectedEnvironment));
            setOutputMarkupId(true);
        }

        private void refresh(AjaxRequestTarget target) {
            target.add(SystemConnectionsFragment.this);
        }

    }

    private UUID actionId;

    class ActionResultFragment extends Fragment {

        Label problemCount;
        ProblemDescriptionListComponent problems;

        ActionResultFragment(String id) {
            super(id, "actionResultFragment", SystemConnectionsPage.this);

            add(problemCount = label("problemCount", () -> {
                int count = systemStore.getActionResult(actionId).getProblemDescriptions().size();
                return "" + count + " problems";
            }));

            add(problems = new ProblemDescriptionListComponent("problemDescriptions", () ->
                    systemStore.getActionResult(actionId).getProblemDescriptions()
            ));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            int count = systemStore.getActionResult(actionId).getProblemDescriptions().size();

            problemCount.setVisible(count > 0);
            problems.setVisible(count > 0);

        }
    }

    private class EnvironmentSettingsFragment extends Fragment {

        EnvironmentSettingsFragment(String id, SerializableSupplier<String> envKey) {
            super(id, "environmentSettingsFragment", SystemConnectionsPage.this);

            add(listView("subsystems", () -> systemStore.getEnvironmentSettings(envKey.get()), (item) -> {
                item.add(label("key", item.getModelObject().getSubSystemKey().getName()));
                item.add(wicketComponentRegistry.render("subsystemSettings", item.getModelObject().getConnectionDetails(), ConnectionDetailsView.class));
            }));
        }

    }

}
