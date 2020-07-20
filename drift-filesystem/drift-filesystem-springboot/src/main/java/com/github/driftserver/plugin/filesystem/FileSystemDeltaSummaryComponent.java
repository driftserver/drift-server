package com.github.driftserver.plugin.filesystem;

import com.github.driftserver.filesystem.FileSystemDelta;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import static com.github.driftserver.ui.infra.WicketUtil.div;
import static com.github.driftserver.ui.infra.WicketUtil.label;

public class FileSystemDeltaSummaryComponent extends Panel {
    public FileSystemDeltaSummaryComponent(String id, FileSystemDelta fileSystemDelta) {
        super(id);
        String name = fileSystemDelta.getSubSystem();
        int count = fileSystemDelta.getSummary().count;
        WebMarkupContainer changes, noChanges;

        add(label("name", name));

        add(changes = div("changes"));
        changes
                .add(label("count", count));
        changes
                .setVisible(count > 0);

        add(noChanges = div("noChanges"));
        noChanges
                .setVisible(count == 0);
    }
}
