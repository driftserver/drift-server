package io.drift.plugin.filesystem;

import io.drift.core.recording.ProblemDescription;
import io.drift.core.system.SubSystemKey;
import io.drift.core.system.connectivity.SubSystemConnectivityActionContext;
import io.drift.core.systemdescription.SystemConnectivityTestContribution;
import io.drift.filesystem.DirectorySettings;
import io.drift.filesystem.FileSystemSettings;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.drift.plugin.filesystem.DriftFileSystemAutoConfig.FILESYSTEM_SUBSYSTEM_TYPE;
import static io.drift.plugin.filesystem.FileSystemExceptionWrapper.wrap;

@Component
public class FileSystemConnectivityTestContribution implements SystemConnectivityTestContribution {

    @Override
    public String getSubSystemType() {
        return FILESYSTEM_SUBSYSTEM_TYPE;
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
