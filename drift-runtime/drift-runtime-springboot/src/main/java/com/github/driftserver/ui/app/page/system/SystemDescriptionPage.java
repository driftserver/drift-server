package com.github.driftserver.ui.app.page.system;

import com.github.driftserver.ui.app.flux.systemdescription.EnvironmentDTO;
import com.github.driftserver.ui.app.flux.systemdescription.SubSystemDTO;
import com.github.driftserver.ui.app.flux.systemdescription.SystemStore;
import com.github.driftserver.ui.app.page.layout.MainLayout;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import static com.github.driftserver.ui.infra.WicketUtil.label;
import static com.github.driftserver.ui.infra.WicketUtil.listView;

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
