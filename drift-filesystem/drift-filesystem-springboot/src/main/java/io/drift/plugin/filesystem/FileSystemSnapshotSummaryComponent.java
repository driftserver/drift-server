package io.drift.plugin.filesystem;

import io.drift.filesystem.FileSystemSnapshot;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.label;

public class FileSystemSnapshotSummaryComponent extends Panel {

    public FileSystemSnapshotSummaryComponent(String id, FileSystemSnapshot fileSystemSnapshot) {
        super(id);
        String name = fileSystemSnapshot.getSubSystem();
        add(label("name", name));
    }
}