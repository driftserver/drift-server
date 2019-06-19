package io.drift.ui.app.page.system;

import io.drift.ui.app.flux.systemdescription.EnvironmentDTO;
import io.drift.ui.app.flux.systemdescription.SubSystemDTO;
import io.drift.ui.app.flux.systemdescription.SystemStore;
import io.drift.ui.app.page.layout.MainLayout;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import static io.drift.ui.infra.WicketUtil.label;
import static io.drift.ui.infra.WicketUtil.listView;

@MountPath("system")
public class SystemDescriptionPage extends MainLayout {

    @SpringBean
    SystemStore systemStore;

    public SystemDescriptionPage() {

        add(listView("environments", systemStore.getEnvironments(), (item)-> {
            EnvironmentDTO environmentDTO = item.getModelObject();
            item.add(label("key", environmentDTO.getKey()));
            item.add(label("description", environmentDTO.getDisplayName()));
        }));

        add(listView("subSystems", systemStore.getSubsystems(), (item)-> {
            SubSystemDTO subSystemDTO = item.getModelObject();
            item.add(label("key", subSystemDTO.getKey()));
            item.add(label("type", subSystemDTO.getType()));
            item.add(label("description", subSystemDTO.getDescription()));
        }));

    }

}
