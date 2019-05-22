package io.drift.ui.app.page.recording;

import io.drift.core.recording.RecordingId;
import io.drift.core.recording.RecordingStep;
import io.drift.core.recording.SubSystemDescription;
import io.drift.core.recording.SystemInteraction;
import io.drift.ui.app.flux.RecordingActions;
import io.drift.ui.app.flux.RecordingStore;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.config.WicketComponentRegistry;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serializable;

import static io.drift.ui.infra.WicketUtil.*;

@MountPath("recording")
public class RecordingPage extends MainLayout {

    @SpringBean
    RecordingActions recordingActions;

    @SpringBean
    RecordingStore recorderStore;

    private RecorderPageState state;

    class PagingState implements Serializable {

        private Integer idx;

        private int maxIdx;

        public int getSelection() {
            return idx;
        }

        public boolean isSelected() {
            return idx != null;
        }

        public void decrease() {
            if (idx != null && idx > 0)
                idx--;
        }

        public void increase() {
            if (idx < maxIdx)
                idx++;
        }

    }

    class RecorderPageState implements Serializable {
        private RecordingId recordingId;
        private Integer selectedSystemInteractionIdx;
        private Integer selectedScenarioItemIdx;

        public RecordingId getRecordingId() {
            return recordingId;
        }

        public int getSelectedScenarioItemIdx() {
            return selectedScenarioItemIdx;
        }

        public int getSelectedSystemInteractionIdx() {
            return selectedSystemInteractionIdx;
        }

        public void setRecordingId(RecordingId recordingId) {
            this.recordingId = recordingId;
        }

        public void setSelectedSystemInteractionIdx(Integer selectedSystemInteractionIdx) {
            this.selectedSystemInteractionIdx = selectedSystemInteractionIdx;
        }

        public void setSelectedScenarioItemIdx(Integer selectedScenarioItemIdx) {
            this.selectedScenarioItemIdx = selectedScenarioItemIdx;
        }

        public boolean isScenarioItemselected() {
            return selectedScenarioItemIdx != null;
        }

        public boolean isSystemInteractionSelected() {
            return selectedSystemInteractionIdx != null;
        }

        public void decreaseSystemInteractionIdx() {
            if (selectedSystemInteractionIdx > 0)
                selectedSystemInteractionIdx--;
        }

        public void increaseSystemInteractionIdx() {
            if (selectedSystemInteractionIdx < getSelectedScenarioItem().getSystemInteractions().size() - 1)
                selectedSystemInteractionIdx++;
        }

        public void decreaseSelectedScenarioItemIdx() {
            if (selectedScenarioItemIdx > 0)
                selectedScenarioItemIdx--;
        }

        public void increaseSelectedScenarioItemIdx() {
            if (selectedScenarioItemIdx < recorderStore.getRecording(state.getRecordingId()).getSteps().size() - 1)
                selectedScenarioItemIdx++;
        }

    }


    public class ScenarioStepDetailsFragment extends Fragment {
        public ScenarioStepDetailsFragment(String id) {
            super(id, "scenarioStepDetailsFragment", RecordingPage.this);
            add(label("title", "[title]"));
        }
    }

    private RecordingStep getSelectedScenarioItem() {
        return recorderStore.getRecordingStep(state.getRecordingId(), state.getSelectedScenarioItemIdx());
    }
    private SystemInteraction getSelectedSystemInteraction() {
        return getSelectedScenarioItem().getSystemInteractions().get(state.getSelectedSystemInteractionIdx());
    }
    private SubSystemDescription getSelectedSubSystem() {
        return recorderStore.getSubSystemDescription(state.getRecordingId(), getSelectedSystemInteraction().getSubSystem());
    }

    public class SystemInteractionsFragment extends Fragment {
        public SystemInteractionsFragment(String id) {
            super(id, "systemInteractionsFragment", RecordingPage.this);
            add(ajaxLink("previousButton", target -> {
                state.decreaseSystemInteractionIdx();
                target.add(scenarioRecorder);
            }));
            add(ajaxLink("nextButton", target -> {
                state.increaseSystemInteractionIdx();
                target.add(scenarioRecorder);
            }));
            add(listView("interactions", getSelectedScenarioItem().getSystemInteractions(), item -> {
                SystemInteraction systemInteraction = item.getModelObject();
                int index = item.getIndex();
                Link<Void> select = ajaxLink("select", target -> {
                    state.setSelectedSystemInteractionIdx(index);
                    target.add(scenarioRecorder);
                });
                select.add(createSystemInteraction("interactionThumbnail", systemInteraction));
                item.add(select);
            }));
            if (state.isSystemInteractionSelected()) {
                add(createSystemInteractionDetail("interactionDetail", getSelectedSystemInteraction(), getSelectedSubSystem()));
            } else {
                add(label("interactionDetail"));
            }
        }
    }

    @SpringBean
    private WicketComponentRegistry registry;

    private Component createSystemInteraction(String id, SystemInteraction systemInteraction) {
        return registry.render(id, systemInteraction, SystemInteractionSummaryView.class);
    }

    private Component createSystemInteractionDetail(String id, SystemInteraction systemInteraction, SubSystemDescription subSystemDescription) {
        return registry.render(id, systemInteraction, SystemInteractionDetailView.class, subSystemDescription);
    }

    public class ScenarioFragment extends Fragment {

        public ScenarioFragment(String id) {
            super(id, "scenarioFragment", RecordingPage.this);
            add(listView("steps", indices(recorderStore.getRecording(state.getRecordingId()).getSteps()), stepItem -> {
                Integer idx = stepItem.getModelObject();
                Link<Void> select = ajaxLink("select", target -> {
                    state.setSelectedScenarioItemIdx(idx);
                    target.add(scenarioRecorder);
                });
                stepItem.add(select);
                if (state.isScenarioItemselected() && idx.equals(state.getSelectedScenarioItemIdx())) {
                    addClass(select, "active");
                }
                select.add(label("title", idx));
            }));
            add(ajaxLink("previous", target -> {
                state.decreaseSelectedScenarioItemIdx();
                target.add(scenarioRecorder);
            }));
            add(ajaxLink("next", target -> {
                state.increaseSelectedScenarioItemIdx();
                target.add(scenarioRecorder);
            }));
        }
    }

    class RecorderControlsFragment extends Fragment {

        public RecorderControlsFragment(String id) {
            super(id, "recorderControlsFragment", RecordingPage.this);
            add(ajaxLink("startRecording", target -> {
                recordingActions.start(state.getRecordingId());
            }));
            add(ajaxLink("takeSnapshot", target -> {
                recordingActions.takeSnapshot(state.getRecordingId());
                state.setSelectedScenarioItemIdx(recorderStore.getRecordingStepCount(state.getRecordingId()) - 1);
                target.add(scenarioRecorder);
            }));
            add(ajaxLink("stopRecording", target -> {
                recordingActions.finish(state.getRecordingId());
            }));
            add(ajaxLink("saveRecording", target -> {
                recordingActions.save(state.getRecordingId());
            }));

        }
    }

    public class ScenarioRecorderFragment extends Fragment {

        private Component scenario, systemInteractions; // , stepDetails

        public ScenarioRecorderFragment(String id) {
            super(id, "scenarioRecorderFragment", RecordingPage.this);
            add(scenario = label("scenario", "[Scenario]"));
            // add(stepDetails = label("stepDetails", "[Step Details]"));
            add(systemInteractions = label("systemInteractions", "[System Interactions]"));
            scenario.setOutputMarkupId(true);
            //stepDetails.setOutputMarkupId(true);
        }

        @Override
        protected void onConfigure() {
            // stepDetails.setVisible(state.isScenarioItemselected());
            systemInteractions.setVisible(state.isScenarioItemselected());
            super.onConfigure();
        }

        @Override
        protected void onBeforeRender() {
            replace(scenario = new ScenarioFragment("scenario"));
            if (state.isScenarioItemselected()) {
                // replace(stepDetails = new ScenarioStepDetailsFragment("stepDetails"));
                replace(systemInteractions = new SystemInteractionsFragment("systemInteractions"));
            }
            super.onBeforeRender();
        }

    }

    private ScenarioRecorderFragment scenarioRecorder;

    public RecordingPage(PageParameters pageParameters) {

        state = new RecorderPageState();
        state.setRecordingId(new RecordingId(pageParameters.get("id").toString()));

        add(new RecorderControlsFragment("recorderControls"));
        add(scenarioRecorder = new ScenarioRecorderFragment("recordedScenario"));

        scenarioRecorder.setOutputMarkupId(true);
    }

}
