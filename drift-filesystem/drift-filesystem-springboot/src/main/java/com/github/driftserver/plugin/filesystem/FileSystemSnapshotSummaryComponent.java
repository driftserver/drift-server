package com.github.driftserver.plugin.filesystem;

import com.github.driftserver.filesystem.FileSystemSnapshot;
import org.apache.wicket.markup.html.panel.Panel;

import static com.github.driftserver.ui.infra.WicketUtil.label;

public class FileSystemSnapshotSummaryComponent extends Panel {

    public FileSystemSnapshotSummaryComponent(String id, FileSystemSnapshot fileSystemSnapshot) {
        super(id);
        String name = fileSystemSnapshot.getSubSystem();
        add(label("name", name));
    }
}