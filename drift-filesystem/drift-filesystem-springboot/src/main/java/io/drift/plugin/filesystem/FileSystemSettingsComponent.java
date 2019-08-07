package io.drift.plugin.filesystem;

import io.drift.filesystem.FileSystemSettings;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.label;
import static io.drift.ui.infra.WicketUtil.listView;

public class FileSystemSettingsComponent extends Panel {

    public FileSystemSettingsComponent(String id, FileSystemSettings fileSystemSettings) {
        super(id);
        add(listView("paths", () -> fileSystemSettings.getDirectories(), listItem -> {
            listItem.add(label("path", listItem.getModelObject().getPath()));
        }));
    }
}
