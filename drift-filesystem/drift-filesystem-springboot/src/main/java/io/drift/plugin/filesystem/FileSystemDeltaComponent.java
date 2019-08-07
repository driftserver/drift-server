package io.drift.plugin.filesystem;

import io.drift.filesystem.FileDelta;
import io.drift.filesystem.FileSystemDelta;
import io.drift.filesystem.FileSystemSettings;
import org.apache.wicket.markup.html.panel.Panel;

import static io.drift.ui.infra.WicketUtil.label;
import static io.drift.ui.infra.WicketUtil.listView;

public class FileSystemDeltaComponent extends Panel {

    public FileSystemDeltaComponent(String id, FileSystemDelta fileSystemDelta, FileSystemSettings fileSystemSettings) {
        super(id);
        add(listView("paths", () -> fileSystemSettings.getDirectories(), listItemOuter -> {
            String pathOuter = listItemOuter.getModelObject().getPath();
            listItemOuter.add(label("path", pathOuter));
            listItemOuter.add(listView("paths", () -> fileSystemDelta.getItems().get(pathOuter), listItemInner -> {
                String pathInner = listItemInner.getModelObject().getPath();
                String fsType = listItemInner.getModelObject() instanceof FileDelta ? "-" : "d";
                String deltaType = listItemInner.getModelObject().getDeltaType().toString();
                listItemInner.add(label("fsType", fsType));
                listItemInner.add(label("deltaType", deltaType));
                listItemInner.add(label("path", pathInner));
            }));
        }));
    }

}
