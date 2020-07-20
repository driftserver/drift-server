package com.github.driftserver.filesystem;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class DriftFileSystemJacksonModule extends SimpleModule {

    public DriftFileSystemJacksonModule() {
        super();
        registerSubtypes(
                FileSystemSettings.class,
                DirectorySettings.class,

                FileSystemSnapshot.class,
                FileSnapshot.class,
                DirectorySnapshot.class,

                FileSystemDelta.class,
                FileDelta.class,
                DirectoryDelta.class
        );
    }

}
