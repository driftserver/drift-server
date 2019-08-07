package io.drift.plugin.filesystem;

import io.drift.core.system.SubSystemKey;
import io.drift.filesystem.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileSystemSession {

    private SubSystemKey subSystemKey;
    private FileSystemSettings fileSystemSettings;

    private class DeltaGeneratingListener extends FileAlterationListenerAdaptor {

        @Override
        public void onDirectoryChange(File dir) {
            deltaItemList.add(new DirectoryDelta(dir.getPath(), FileSystemDeltaType.CHANGED));
        }

        @Override
        public void onDirectoryCreate(File dir) {
            deltaItemList.add(new DirectoryDelta(dir.getPath(), FileSystemDeltaType.CREATED));
        }

        @Override
        public void onDirectoryDelete(File dir) {
            deltaItemList.add(new DirectoryDelta(dir.getPath(), FileSystemDeltaType.DELETED));
        }

        @Override
        public void onFileChange(File file) {
            deltaItemList.add(new FileDelta(file.getPath(), FileSystemDeltaType.CHANGED));
        }

        @Override
        public void onFileCreate(File file) {
            deltaItemList.add(new FileDelta(file.getPath(), FileSystemDeltaType.CREATED));
        }

        @Override
        public void onFileDelete(File file) {
            deltaItemList.add(new FileDelta(file.getPath(), FileSystemDeltaType.DELETED));
        }

        private List<FileSystemDeltaItem> deltaItemList = new ArrayList<>();

        public void resetDeltaItemList() {
            deltaItemList = new ArrayList<>();
        }

        public List<FileSystemDeltaItem> getDeltaItemList() {
            return deltaItemList;
        }
    }


    private Map<String, FileAlterationObserver> observers = new HashMap();
    private Map<String, DeltaGeneratingListener> listeners = new HashMap();

    public FileSystemSession(SubSystemKey subSystemKey, FileSystemSettings fileSystemSettings) {
        this.subSystemKey = subSystemKey;
        this.fileSystemSettings = fileSystemSettings;
    }

    public FileSystemSnapshot takeSnapshot() {
        FileSystemSnapshot snapshot = new FileSystemSnapshot();
        snapshot.setSubSystem(subSystemKey.getName());
        for (DirectorySettings directorySettings : fileSystemSettings.getDirectories()) {
            String path = directorySettings.getPath();
            List<FileSystemSnapshotItem> items =
                    FileUtils.listFilesAndDirs(new File(path), TrueFileFilter.TRUE, TrueFileFilter.TRUE).stream()
                            .map(file -> file.isDirectory() ? new FileSnapshot(file.getPath()) : new DirectorySnapshot(file.getPath()))
                            .collect(Collectors.toList());
            snapshot.getItems().put(path, items);
        }
        return snapshot;
    }

    public void init() throws Exception {
        for (DirectorySettings directorySettings : fileSystemSettings.getDirectories()) {
            String dir = directorySettings.getPath();

            FileAlterationObserver observer = new FileAlterationObserver(new File(dir));
            observers.put(dir, observer);

            DeltaGeneratingListener listener = new DeltaGeneratingListener();
            listeners.put(dir, listener);

            observer.addListener(listener);
            observer.initialize();
        }
    }

    public FileSystemDelta getDelta() {

        FileSystemDelta fileSystemDelta = new FileSystemDelta();

        for (DirectorySettings directorySettings : fileSystemSettings.getDirectories()) {
            String dir = directorySettings.getPath();

            FileAlterationObserver observer = observers.get(dir);
            DeltaGeneratingListener listener = listeners.get(dir);

            observer.checkAndNotify();
            fileSystemDelta.getItems().put(dir, listener.getDeltaItemList());
            fileSystemDelta.setSubSystem(subSystemKey.getName());
            listener.resetDeltaItemList();
        }

        return fileSystemDelta;
    }

    public void destroy() throws Exception {
        for (FileAlterationObserver observer : observers.values()) {
            observer.destroy();
        }
    }

    public SubSystemKey getSubSystemKey() {
        return subSystemKey;
    }

    public FileSystemSettings getFileSystemSettings() {
        return fileSystemSettings;
    }
}
