package io.drift.filesystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyFileSystemDeltaBuilder {

    public FileSystemDelta fileSystemDelta() {

        FileSystemDelta fileSystemDelta = new FileSystemDelta();

        Map<String, List<FileSystemDeltaItem>> items = new HashMap();

        {
            List<FileSystemDeltaItem> list = new ArrayList<>();
            list.add(new FileDelta("/a/b/c.txt", FileSystemDeltaType.CREATED));
            list.add(new FileDelta("/a/b/d.txt", FileSystemDeltaType.CHANGED));
            list.add(new FileDelta("/a/b/e.txt", FileSystemDeltaType.DELETED));

            items.put("/a/b", list);
        }
        {
            List<FileSystemDeltaItem> list = new ArrayList<>();
            list.add(new DirectoryDelta("/f/g/h", FileSystemDeltaType.CREATED));
            list.add(new DirectoryDelta("/f/g/i", FileSystemDeltaType.CHANGED));
            list.add(new DirectoryDelta("/f/g/j", FileSystemDeltaType.DELETED));

            items.put("/f/g", list);
        }

        fileSystemDelta.setItems(items);

        return fileSystemDelta;


    }

}
