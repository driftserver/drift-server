package com.github.driftserver.plugin.filesystem;

import com.github.driftserver.core.infra.logging.ProblemDescription;
import com.github.driftserver.core.system.SubSystemKey;
import com.github.driftserver.core.system.connectivity.SubSystemConnectivityActionContext;
import com.github.driftserver.core.systemdescription.SystemConnectivityTestContribution;
import com.github.driftserver.filesystem.DirectorySettings;
import com.github.driftserver.filesystem.FileSystemSettings;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.driftserver.plugin.filesystem.FileSystemExceptionWrapper.wrap;

@Component
public class FileSystemConnectivityTestContribution implements SystemConnectivityTestContribution {

    @Override
    public String getSubSystemType() {
        return DriftFileSystemAutoConfig.FILESYSTEM_SUBSYSTEM_TYPE;
    }

    @Override
    public void asyncTestConnectivity(SubSystemConnectivityActionContext actionContext) {
        SubSystemKey subSystemKey = actionContext.getSubSystem().getKey();
        String location = subSystemKey.getName();
        String action = null;

        try {
            action = "getting file system settings";
            FileSystemSettings fileSystemSettings = (FileSystemSettings) actionContext.getConnectionDetails();
            for(DirectorySettings directorySettings : fileSystemSettings.getDirectories()) {
                String path = directorySettings.getPath();
                action = "checking dir" + path;
                if (!Files.isDirectory(Paths.get(path))) {
                    actionContext.getActionLogger().addProblem(new ProblemDescription(location, action, new Exception("dir not found")));
                }
            }
        } catch (Exception e) {
            actionContext.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
        }
        actionContext.setFinished();

    }
}
