package io.drift.plugin.elasticsearch;

import io.drift.core.recording.ProblemDescription;
import io.drift.core.recording.RecordingContext;
import io.drift.core.recording.RecordingId;
import io.drift.core.recording.RecordingSessionContribution;
import io.drift.core.system.SubSystemConnectionDetails;
import io.drift.core.system.SubSystemKey;
import io.drift.elasticsearch.ElasticSearchSettings;
import io.drift.elasticsearch.ElasticSearchSnapshot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.drift.plugin.elasticsearch.DriftElasticSearchAutoConfig.ELASTICSEARCH_SUBSYSTEM_TYPE;
import static io.drift.plugin.elasticsearch.ElasticSearchExceptionWrapper.wrap;

@Component
public class ElasticSearchRecordingContribution implements RecordingSessionContribution {

    private Map<RecordingId, List<ElasticSearchSession>> sessionsById = new HashMap<>();

    @Override
    public void onConnect(RecordingContext recordingContext) {
        Map<SubSystemKey, SubSystemConnectionDetails> ElasticSearchs = recordingContext.getSubSystems(ELASTICSEARCH_SUBSYSTEM_TYPE);
        List<ElasticSearchSession> sessions = new ArrayList<>();
        sessionsById.put(recordingContext.getRecordingId(), sessions);

        ElasticSearchs.forEach((subSystemKey, fileSubSystem) -> {
            String location = subSystemKey.getName();
            String action = null;
            try {
                action = "setting up file system tracker";
                ElasticSearchSettings ElasticSearchSettings = (ElasticSearchSettings) fileSubSystem;
                sessions.add(new ElasticSearchSession(subSystemKey, ElasticSearchSettings));
            } catch(Exception e) {
                recordingContext.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
            }
        });

    }

    @Override
    public void initialize(RecordingContext context) {
        List<ElasticSearchSession> sessions = sessionsById.get(context.getRecordingId());

        sessions.forEach(session -> {
            String location = session.getSubSystemKey().getName();
            String action = null;
            String subSystemName = session.getSubSystemKey().getName();
            try {
                action = "taking initial snapshot";
                ElasticSearchSnapshot ElasticSearchSnapshot = session.takeSnapshot();
                session.init();

                action = "adding ElasticSearch recording settings as subsystem description to recording";
                context.getRecording().addSubSystemDescription(subSystemName, session.getElasticSearchSettings());

                action = "saving snapshot as initial state of the ElasticSearch ";
                context.getRecording().getInitialState().addSubSystemState(subSystemName, ElasticSearchSnapshot);

                action = "saving snapshot as current final state of the ElasticSearch";
                context.getRecording().getFinalstate().addSubSystemState(subSystemName, ElasticSearchSnapshot);

            } catch(Exception e) {
                context.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
            }
        });
    }

    @Override
    public void takeSnapshot(RecordingContext context) {
        List<ElasticSearchSession> sessions = sessionsById.get(context.getRecordingId());

        sessions.forEach(session -> {
            String location = session.getSubSystemKey().getName();
            String action = null;
            String subSystemName = session.getSubSystemKey().getName();
            try {

                action = "adding delta to system interactions";
                context.getCurrentStep().getSystemInteractions().add(session.getDelta());

                action = "taking snapshot";
                ElasticSearchSnapshot ElasticSearchSnapshot = session.takeSnapshot();

                action = "saving snapshot as current final state of the ElasticSearch";
                context.getRecording().getFinalstate().addSubSystemState(subSystemName, ElasticSearchSnapshot);

            } catch(Exception e) {
                context.getActionLogger().addProblem(new ProblemDescription(location, action, wrap(e)));
            }
        });
    }

    @Override
    public void onDisconnect(RecordingContext context) {
        List<ElasticSearchSession> sessions = sessionsById.get(context.getRecordingId());

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
