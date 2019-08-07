package io.drift.plugin.filesystem;

import io.drift.filesystem.FileSnapshot;
import io.drift.filesystem.FileSystemSettings;
import io.drift.filesystem.FileSystemSnapshot;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.label;
import static io.drift.ui.infra.WicketUtil.listView;

public class FileSystemSnapshotDetailComponent extends Panel {

    public FileSystemSnapshotDetailComponent(String id, FileSystemSnapshot fileSystemSnapshot, FileSystemSettings fileSystemSettings) {
        super(id);
        add(listView("paths", () -> fileSystemSettings.getDirectories(), listItemOuter -> {
            String pathOuter = listItemOuter.getModelObject().getPath();
            listItemOuter.add(label("path", pathOuter));
            listItemOuter.add(listView("paths", () -> fileSystemSnapshot.getItems().get(pathOuter), listItemInner -> {
                String typeInner = listItemInner.getModelObject() instanceof FileSnapshot ? "d" : "-";
                String pathInner = listItemInner.getModelObject().getPath();
                listItemInner.add(label("type", typeInner));
                listItemInner.add(label("path", pathInner));
            }));
        }));
    }

}
