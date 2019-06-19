package io.drift.ui.app.page.system;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import io.drift.ui.app.flux.systemdescription.SubSystemSettingsDTO;
import io.drift.ui.app.flux.systemdescription.SystemStore;
import io.drift.ui.app.page.layout.MainLayout;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serializable;
import java.util.List;

import static io.drift.ui.infra.WicketUtil.label;
import static io.drift.ui.infra.WicketUtil.listView;

@MountPath("system")
@WicketHomePage
public class SystemPage  extends MainLayout {

    @SpringBean
    SystemStore systemStore;

    class SystemPageState implements Serializable {
        String selectedEnvironment;
    }

    private SystemPageState pageState = new SystemPageState();

    private class EnvironmentSettingsFragment extends Fragment {

        EnvironmentSettingsFragment(String id, List<SubSystemSettingsDTO> settings) {
            super(id, "environmentSettingsFragment", SystemPage.this);
            add(listView("subsystems", settings, (item)-> {
                item.add(label("subsystemSettings", item.getModelObject().getName()));
            }));
        }

    }

    public SystemPage() {

        pageState.selectedEnvironment = systemStore.getEnvironments().get(0).getDisplayName();

        add(listView("environments", systemStore.getEnvironments(), (item)-> {
            item.add(label("environment", item.getModelObject().getDisplayName()));
        }));

        add(listView("subSystems", systemStore.getSubsystems(), (item)-> {
            item.add(label("subSystem", item.getModelObject().getName()));
        }));

        add(new EnvironmentSettingsFragment("environmentSettings", systemStore.getEnvironmentSettings(pageState.selectedEnvironment)));


    }


}
