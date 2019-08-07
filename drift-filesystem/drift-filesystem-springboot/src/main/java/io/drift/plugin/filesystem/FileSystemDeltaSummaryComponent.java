package io.drift.plugin.filesystem;

import io.drift.filesystem.FileSystemDelta;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.div;
import static io.drift.ui.infra.WicketUtil.label;

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
