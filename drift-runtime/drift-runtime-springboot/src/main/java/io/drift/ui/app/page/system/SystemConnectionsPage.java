package io.drift.ui.app.page.system;

import io.drift.core.infra.logging.ProblemDescription;
import io.drift.core.system.EnvironmentKey;
import io.drift.core.system.connectivity.EnvironmentConnectivityActionContext;
import io.drift.ui.app.component.stacktrace.ProblemDescriptionListComponent;
import io.drift.ui.app.flux.systemdescription.SystemActions;
import io.drift.ui.app.flux.systemdescription.SystemStore;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.config.WicketComponentRegistry;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.danekja.java.util.function.serializable.SerializableSupplier;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;

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

    private class SystemConnectionsFragment extends Fragment {

        String selectedEnvironment;
        int behaviorId;

        public SystemConnectionsFragment(String id) {
            super(id, "systemConnectionsFragment", SystemConnectionsPage.this);

            selectedEnvironment = systemStore.getEnvironments().get(0).getKey();

            Link test = ajaxLink("testConnectionSettings", (target -> {
                resetSelfUpdatingBehavior();
                systemActions.asyncTestConnections(new EnvironmentKey(selectedEnvironment));
                startSelfUpdatingBehavior();
                refresh(target);
            }));
            test.add(label("envKey", () -> selectedEnvironment));

            add(test);

            add(listView("environments", () -> systemStore.getEnvironments(), (item) -> {
                String environment = item.getModelObject().getKey();

                Link select = ajaxLink("select", (target) -> {
                    selectedEnvironment = environment;
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

        private void startSelfUpdatingBehavior() {
            Behavior behavior = new AjaxSelfUpdatingTimerBehavior(Duration.milliseconds(300)) {
                @Override
                public void onEvent(Component component, IEvent<?> event) {
                    EnvironmentConnectivityActionContext actionContext = systemStore.getActionContext(selectedEnvironment);
                    if (actionContext == null || actionContext.isFinished()) {
                        resetSelfUpdatingBehavior();
                    }

                }
            };
            add(behavior);
            behaviorId = getBehaviorId(behavior);
        }

        private void resetSelfUpdatingBehavior() {
            try {
                remove(getBehaviorById(behaviorId));
            } catch (Exception e) {
                // ignore if behavior is already removed
            }
        }

    }


    class ProblemIndicatorFragment extends Fragment {

        SerializableSupplier<String> envKey;
        SerializableSupplier<String> subSystemKey;

        Label problemCount;
        ProblemDescriptionListComponent problems;

        ProblemIndicatorFragment(String id, SerializableSupplier<String> envKey, SerializableSupplier<String> subSystemKey) {
            super(id, "problemIndicatorFragment", SystemConnectionsPage.this);
            this.envKey = envKey;
            this.subSystemKey = subSystemKey;

            add(problemCount = label("problemCount", () -> {
                int count = getproblemdescriptions().size();
                return "" + count + " problem" + (count == 1 ? "" : "s");
            }));

            add(problems = new ProblemDescriptionListComponent("problemDescriptions", () ->
                getproblemdescriptions()
            ));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            int count = getproblemdescriptions().size();

            problemCount.setVisible(count > 0);
            problems.setVisible(count > 0);

        }

        private List<ProblemDescription> getproblemdescriptions() {
            return systemStore.getActionContext(envKey.get(), subSystemKey.get()).getActionLogger().getProblemDescriptions();
        }
    }

    private class ConnectivityCheckStatusFragment extends Fragment {

        SerializableSupplier<String> envKey;
        WebMarkupContainer inProgress, ok, notOK;
        SerializableSupplier<String> subSystemKey;

        ConnectivityCheckStatusFragment(String id, SerializableSupplier<String> envKey, SerializableSupplier<String> subSystemKey) {

            super(id, "connectivityCheckStatusFragment", SystemConnectionsPage.this);
            this.envKey = envKey;
            this.subSystemKey = subSystemKey;

            add(inProgress = div("check_in_progress"));
            add(ok = div("check_ok"));
            add(notOK = div("check_not_ok"));

            notOK.add(new ProblemIndicatorFragment("problemIndicator", envKey, subSystemKey));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            EnvironmentConnectivityActionContext actionContext = systemStore.getActionContext(envKey.get());
            inProgress.setVisible(actionContext != null && !actionContext.isFinished(subSystemKey.get()));
            ok.setVisible(actionContext != null && actionContext.isFinished(subSystemKey.get()) && !actionContext.hasProblems(subSystemKey.get()));
            notOK.setVisible(actionContext != null && actionContext.isFinished(subSystemKey.get()) && actionContext.hasProblems(subSystemKey.get()));

        }

    }


    private class EnvironmentSettingsFragment extends Fragment {

        EnvironmentSettingsFragment(String id, SerializableSupplier<String> envKey) {
            super(id, "environmentSettingsFragment", SystemConnectionsPage.this);

            add(listView("subsystems", () -> systemStore.getEnvironmentSettings(envKey.get()), (item) -> {
                String subSystemKey = item.getModelObject().getSubSystemKey().getName();
                item.add(label("key", subSystemKey));
                item.add(wicketComponentRegistry.render("subsystemSettings", item.getModelObject().getConnectionDetails(), ConnectionDetailsView.class));
                item.add(new ConnectivityCheckStatusFragment("connectivityCheckStatus", envKey, () -> subSystemKey));
            }));
        }

    }


}
