package com.github.driftserver.plugin.filesystem;

import com.github.driftserver.filesystem.FileSystemSettings;
import org.apache.wicket.markup.html.panel.Panel;

import static com.github.driftserver.ui.infra.WicketUtil.label;
import static com.github.driftserver.ui.infra.WicketUtil.listView;

public class FileSystemSettingsComponent extends Panel {

    public FileSystemSettingsComponent(String id, FileSystemSettings fileSystemSettings) {
        super(id);
        add(listView("paths", () -> fileSystemSettings.getDirectories(), listItem -> {
            listItem.add(label("path", listItem.getModelObject().getPath()));
        }));
    }
}
