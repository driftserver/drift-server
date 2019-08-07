package io.drift.filesystem;

import java.util.ArrayList;
import java.util.List;

public class DummyFileSystemSnapshotBuilder {


    public FileSystemSnapshot fileSystemSnapshot() {
        FileSystemSnapshot snapshot = new FileSystemSnapshot();

        {
            List<FileSystemSnapshotItem> items = new ArrayList<>();
            snapshot.getItems().put("a", items);
            {
                items.add(new DirectorySnapshot("a/b"));
                items.add(new DirectorySnapshot("a/b/c"));
                items.add(new FileSnapshot("a/b/c/d.txt"));
            }
        }
        {
            List<FileSystemSnapshotItem> items = new ArrayList<>();
            snapshot.getItems().put("e", items);
            {
                items.add(new DirectorySnapshot("e/f"));
                items.add(new DirectorySnapshot("e/f/g"));
                items.add(new FileSnapshot("e/f/g/h.txt"));
            }
        }

        return snapshot;
    }

}
