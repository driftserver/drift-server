package com.github.driftserver.plugin.filesystem;

import com.github.driftserver.core.infra.logging.ProblemDescription;
import com.github.driftserver.core.recording.session.RecordingContext;
import com.github.driftserver.core.recording.model.RecordingId;
import com.github.driftserver.core.recording.RecordingSessionContribution;
import com.github.driftserver.core.system.SubSystemConnectionDetails;
import com.github.driftserver.core.system.SubSystemKey;
import com.github.driftserver.filesystem.FileSystemSettings;
import com.github.driftserver.filesystem.FileSystemSnapshot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.driftserver.plugin.filesystem.FileSystemExceptionWrapper.wrap;

@Component
public class FileSystemRecordingContribution implements RecordingSessionContribution {

    private Map<RecordingId, List<FileSystemSession>> sessionsById = new HashMap<>();

    @Override
    public void onConnect(RecordingContext recordingContext) {
        Map<SubSystemKey, SubSystemConnectionDetails> fileSystems = recordingContext.getSubSystems(DriftFileSystemAutoConfig.FILESYSTEM_SUBSYSTEM_TYPE);
        List<FileSystemSession> sessions = new ArrayList<>();
        sessionsById.put(recordingContext.getRecordingId(), sessions);

        fileSystems.forEach((subSystemKey, fileSubSystem) -> {
            String location = subSystemKey.getName();
            String action = null;
            try {
                action = "setting up file system tracker";
                FileSystemSettings fileSystemSettings = (FileSystemSettings) fileSubSystem;
                sessions.add(new FileSystemSession(subSystemKey, fileSystemSettings));
            } catch(Exception e) {
                recordingContext.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
            }
        });

    }

    @Override
    public void initialize(RecordingContext context) {
        List<FileSystemSession> sessions = sessionsById.get(context.getRecordingId());

        sessions.forEach(session -> {
            String location = session.getSubSystemKey().getName();
            String action = null;
            String subSystemName = session.getSubSystemKey().getName();
            try {
                action = "taking initial snapshot";
                FileSystemSnapshot fileSystemSnapshot = session.takeSnapshot();
                session.init();

                action = "adding filesystem recording settings as subsystem description to recording";
                context.getRecording().addSubSystemDescription(subSystemName, session.getFileSystemSettings());

                action = "saving snapshot as initial state of the filesystem ";
                context.getRecording().getInitialState().addSubSystemState(subSystemName, fileSystemSnapshot);

                action = "saving snapshot as current final state of the filesystem";
                context.getRecording().getFinalstate().addSubSystemState(subSystemName, fileSystemSnapshot);

            } catch(Exception e) {
                context.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
            }
        });
    }

    @Override
    public void takeSnapshot(RecordingContext context) {
        List<FileSystemSession> sessions = sessionsById.get(context.getRecordingId());

        sessions.forEach(session -> {
            String location = session.getSubSystemKey().getName();
            String action = null;
            String subSystemName = session.getSubSystemKey().getName();
            try {

                action = "adding delta to system interactions";
                context.getCurrentStep().getSystemInteractions().add(session.getDelta());

                action = "taking snapshot";
                FileSystemSnapshot fileSystemSnapshot = session.takeSnapshot();

                action = "saving snapshot as current final state of the filesystem";
                context.getRecording().getFinalstate().addSubSystemState(subSystemName, fileSystemSnapshot);

            } catch(Exception e) {
                context.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
            }
        });
    }

    @Override
    public void onDisconnect(RecordingContext context) {
        List<FileSystemSession> sessions = sessionsById.get(context.getRecordingId());

        sessions.forEach(session -> {
            String location = session.getSubSystemKey().getName();
            String action = null;
            try {
                session.destroy();
            } catch(Exception e) {
                context.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
            }
        });
    }

    @Override
    public void onReconnect(RecordingContext context) {
        onConnect(context);
    }
}
