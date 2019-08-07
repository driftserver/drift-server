package io.drift.plugin.filesystem;

import io.drift.core.recording.ProblemDescription;
import io.drift.core.recording.RecordingContext;
import io.drift.core.recording.RecordingId;
import io.drift.core.recording.RecordingSessionContribution;
import io.drift.core.system.SubSystemConnectionDetails;
import io.drift.core.system.SubSystemKey;
import io.drift.filesystem.FileSystemSettings;
import io.drift.filesystem.FileSystemSnapshot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.drift.plugin.filesystem.DriftFileSystemAutoConfig.FILESYSTEM_SUBSYSTEM_TYPE;
import static io.drift.plugin.filesystem.FileSystemExceptionWrapper.wrap;

@Component
public class FileSystemRecordingContribution implements RecordingSessionContribution {

    private Map<RecordingId, List<FileSystemSession>> sessionsById = new HashMap<>();

    @Override
    public void onConnect(RecordingContext recordingContext) {
        Map<SubSystemKey, SubSystemConnectionDetails> fileSystems = recordingContext.getSubSystems(FILESYSTEM_SUBSYSTEM_TYPE);
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
