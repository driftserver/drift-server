package com.github.driftserver.plugin.filesystem;

import com.github.driftserver.filesystem.FileDelta;
import com.github.driftserver.filesystem.FileSystemDelta;
import com.github.driftserver.filesystem.FileSystemSettings;
import org.apache.wicket.markup.html.panel.Panel;

import static com.github.driftserver.ui.infra.WicketUtil.label;
import static com.github.driftserver.ui.infra.WicketUtil.listView;

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
