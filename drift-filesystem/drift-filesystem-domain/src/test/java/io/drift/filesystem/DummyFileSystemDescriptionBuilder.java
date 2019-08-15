package io.drift.filesystem;

import io.drift.core.system.*;

public class DummyFileSystemDescriptionBuilder {

    public SystemDescription createDummy() {

        SystemDescription systemDescription = new SystemDescription();

        Environment localEnv = new Environment();
        localEnv.setKey(new EnvironmentKey("LOCAL"));
        localEnv.setName("Local");
        systemDescription.getEnvironments().add(localEnv);

        SubSystem fs1 = new SubSystem();
        fs1.setType("filesystem");
        fs1.setName("Drive C:");
        fs1.setKey(new SubSystemKey("fs1"));
        systemDescription.getSubSystems().add(fs1);

        FileSystemSettings fileSystemSettings = new FileSystemSettings();
        fileSystemSettings.addDirectory(new DirectorySettings("/a/b/c"));
        fileSystemSettings.addDirectory(new DirectorySettings("/e/f/g"));

        systemDescription.addConnectionDetails(fs1.getKey(), localEnv.getKey(), fileSystemSettings);

        return systemDescription;

    }


}
