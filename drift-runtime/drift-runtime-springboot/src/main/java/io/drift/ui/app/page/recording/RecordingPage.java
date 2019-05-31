package io.drift.ui.app.page.recording;

import io.drift.core.recording.*;
import io.drift.ui.app.flux.RecordingActions;
import io.drift.ui.app.flux.RecordingStore;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.config.WicketComponentRegistry;
import io.drift.ui.infra.ListSelector;
import io.drift.ui.infra.Selector;
import org.apache.wicket.Component;
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

    class ScenarioItemSelector extends Selector<Selector> implements Serializable {

        static final String INITIAL_STATE = "initial state";
        static final String SCENARIO_STEP = "scenario step";
        static final String FINAL_STATE = "initial state";

        private String selection;
        private ScenarioStepSelector scenarioStepSelector = new ScenarioStepSelector(this);
        private SystemStateSubSystemSelector systemStateSubSystemSelector = new SystemStateSubSystemSelector(this);

        public ScenarioItemSelector() { super(null);}

        public void selectInitialState() {
            selection = INITIAL_STATE;
        }
        public boolean isInitialStateSelected() {
            return INITIAL_STATE.equals(selection);
        }

        public void selectFinalState() {
            selection = FINAL_STATE;
        }
        public boolean isFinalStateSelected() {
            return FINAL_STATE.equals(selection);
        }

        public void selectScenarioStep(Integer idx) {
            selection = SCENARIO_STEP;
            scenarioStepSelector.select(idx);
        }
        public boolean isScenarioStepSelected() {
            return SCENARIO_STEP.equals(selection);
        }

        public ScenarioStepSelector getScenarioStepSelector() {
            return scenarioStepSelector;
        }

        public SystemStateSubSystemSelector getSystemStateSubSystemSelector() {
            return systemStateSubSystemSelector;
        }

        public SystemState getSelectedSystemState() {
            if (isInitialStateSelected())
                return recorderStore.getRecording(recordingId).getInitialState();
            else if (isFinalStateSelected())
                return recorderStore.getRecording(recordingId).getFinalstate();
            else
                return null;
        }

        public SubSystemDescription getSubSystemDescription(String subSystemKey) {
            return recorderStore.getRecording(recordingId).getSubSystemDescription(subSystemKey);
        }

        public void selectedLastScenarioItem() {
            selectScenarioStep(recorderStore.getRecordingStepCount(recordingId) - 1);
        }
    }
    class SystemInteractionsSelector extends ListSelector<ScenarioStepSelector> {
        public SystemInteractionsSelector(ScenarioStepSelector parentselector) { super(parentselector); }

        public SystemInteraction getSelectedSystemInteraction() {
            return getParentselector().getSelectedScenarioItem().getSystemInteractions().get(getSelection());
        }

        public SubSystemDescription getSelectedSubSystem() {
            return getParentselector().getParentselector().getSubSystemDescription(getSelectedSystemInteraction().getSubSystem());
        }
    }
    class ScenarioStepSelector extends ListSelector<ScenarioItemSelector> {

        private SystemInteractionsSelector systemInteractionsSelector = new SystemInteractionsSelector(this);

        public ScenarioStepSelector(ScenarioItemSelector parentSelector) { super(parentSelector); }

        public RecordingStep getSelectedScenarioItem() {
            return recorderStore.getRecordingStep(recordingId, getSelection());
        }

        public SystemInteractionsSelector getSystemInteractionsSelector() {
            return systemInteractionsSelector;
        }
    }
    class SystemStateSubSystemSelector extends ListSelector<ScenarioItemSelector> {
        public SystemStateSubSystemSelector(ScenarioItemSelector parentSelector) { super(parentSelector); }

        public SubSystemState getSelectedSubSystemState() {
            String selectedSubSystemKey = getSelectedSubSystemKey();
            return getParentselector().getSelectedSystemState().getSubSystemState(selectedSubSystemKey);
        }

        public String getSelectedSubSystemKey() {
            int idx = getSelection();
            return getParentselector().getSelectedSystemState().getOrderedSubSystemKeys().get(idx);
        }

        public SubSystemDescription getSubSystemDescription() {
            return recorderStore.getSubSystemDescription(recordingId, getSelectedSubSystemKey());
        }

    }

    private ScenarioItemSelector scenarioItemSelector;
    private RecordingId recordingId;

    /*
    public class ScenarioStepDetailsFragment extends Fragment {
        public ScenarioStepDetailsFragment(String id) {
            super(id, "scenarioStepDetailsFragment", RecordingPage.this);
            add(label("title", "[title]"));
        }
    }
    */


    public class SystemInteractionsFragment extends Fragment {
        public SystemInteractionsFragment(String id) {
            super(id, "systemInteractionsFragment", RecordingPage.this);
            SystemInteractionsSelector selector = scenarioItemSelector.getScenarioStepSelector().getSystemInteractionsSelector();
            add(ajaxLink("previousButton", target -> {
                selector.decrease();
                target.add(scenarioRecorder);
            }));
            add(ajaxLink("nextButton", target -> {
                selector.increase();
                target.add(scenarioRecorder);
            }));
            add(listView("interactions", scenarioItemSelector.getScenarioStepSelector().getSelectedScenarioItem().getSystemInteractions(), item -> {
                SystemInteraction systemInteraction = item.getModelObject();
                int index = item.getIndex();
                Link<Void> select = ajaxLink("select", target -> {
                    selector.select(index);
                    target.add(scenarioRecorder);
                });
                select.add(createSystemInteraction("interactionThumbnail", systemInteraction));
                item.add(select);
            }));
            if (selector.isSelected()) {
                add(createSystemInteractionDetail("interactionDetail", selector.getSelectedSystemInteraction(), selector.getSelectedSubSystem()));
            } else {
                add(label("interactionDetail"));
            }
        }
    }

    public class SystemStateFragment extends Fragment {
        public SystemStateFragment(String id) {
            super(id, "systemStateFragment", RecordingPage.this);
            SystemStateSubSystemSelector subSystemSelector = scenarioItemSelector.getSystemStateSubSystemSelector();
            add(listView("subSystems", indices(scenarioItemSelector.getSelectedSystemState().getOrderedSubSystemKeys()), item -> {
                Link<Void> select = ajaxLink("selectSubSystem", target -> {
                    subSystemSelector.select(item.getModelObject());
                    target.add(scenarioRecorder);
                });
                item.add(select);
                select.add(label("subSystemName", item.getModelObject()));
            }));
            if (subSystemSelector.isSelected()) {
                add(createSubSystemStateDetail("selectedSubSystemState",subSystemSelector.getSelectedSubSystemState(), subSystemSelector.getSubSystemDescription()));
            } else {
                add(label("selectedSubSystemState", "[selectedSubSystemState]"));
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

    private Component createSubSystemStateDetail(String id, SubSystemState subSystemState, SubSystemDescription subSystemDescription) {
        return registry.render(id, subSystemState, SubSystemStateDetailView.class, subSystemDescription);
    }



    public class ScenarioFragment extends Fragment {


        public ScenarioFragment(String id) {
            super(id, "scenarioFragment", RecordingPage.this);
            add(ajaxLink("selectInitialState", target -> {
                scenarioItemSelector.selectInitialState();
                target.add(scenarioRecorder);
            }));
            add(ajaxLink("selectFinalState", target -> {
                scenarioItemSelector.selectFinalState();
                target.add(scenarioRecorder);
            }));
            add(listView("steps", indices(recorderStore.getRecording(recordingId).getSteps()), stepItem -> {
                Integer idx = stepItem.getModelObject();
                Link<Void> select = ajaxLink("select", target -> {
                    scenarioItemSelector.selectScenarioStep(idx);
                    target.add(scenarioRecorder);
                });
                stepItem.add(select);
                if (scenarioItemSelector.isScenarioStepSelected() && idx.equals(scenarioItemSelector.getScenarioStepSelector().getSelection())) {
                    addClass(select, "active");
                }
                select.add(label("title", idx));
            }));
            add(ajaxLink("previous", target -> {
                scenarioItemSelector.getScenarioStepSelector().decrease();
                target.add(scenarioRecorder);
            }));
            add(ajaxLink("next", target -> {
                scenarioItemSelector.getScenarioStepSelector().increase();
                target.add(scenarioRecorder);
            }));
        }
    }

    class RecorderControlsFragment extends Fragment {

        public RecorderControlsFragment(String id) {
            super(id, "recorderControlsFragment", RecordingPage.this);
            add(ajaxLink("startRecording", target -> {
                recordingActions.start(recordingId);
            }));
            add(ajaxLink("takeSnapshot", target -> {
                recordingActions.takeSnapshot(recordingId);
                scenarioItemSelector.selectedLastScenarioItem();
                target.add(scenarioRecorder);
            }));
            add(ajaxLink("stopRecording", target -> {
                recordingActions.finish(recordingId);
            }));
            Link saveLink;
            add(saveLink = ajaxLink("saveRecording", target -> {
                recordingActions.save(recordingId);
            }));
            saveLink.setEnabled(!recorderStore.getRecorderControlState(recordingId).isAutosave());

        }
    }

    public class ScenarioRecorderFragment extends Fragment {

        private Component scenario, systemInteractions, systemState; // , stepDetails

        public ScenarioRecorderFragment(String id) {
            super(id, "scenarioRecorderFragment", RecordingPage.this);
            add(scenario = label("scenario", "[Scenario]"));
            // add(stepDetails = label("stepDetails", "[Step Details]"));
            add(systemInteractions = label("systemInteractions", "[System Interactions]"));
            add(systemState = label("systemState", "[System State]"));
            scenario.setOutputMarkupId(true);
            //stepDetails.setOutputMarkupId(true);
        }

        @Override
        protected void onConfigure() {
            // stepDetails.setVisible(state.isScenarioItemselected());
            systemInteractions.setVisible(scenarioItemSelector.isScenarioStepSelected());
            systemState.setVisible(scenarioItemSelector.isInitialStateSelected() || scenarioItemSelector.isFinalStateSelected());
            super.onConfigure();
        }

        @Override
        protected void onBeforeRender() {
            replace(scenario = new ScenarioFragment("scenario"));
            if (scenarioItemSelector.isScenarioStepSelected()) {
                // replace(stepDetails = new ScenarioStepDetailsFragment("stepDetails"));
                replace(systemInteractions = new SystemInteractionsFragment("systemInteractions"));
            }
            if (scenarioItemSelector.isInitialStateSelected() || scenarioItemSelector.isFinalStateSelected()) {
                replace(systemState = new SystemStateFragment("systemState"));
            }

            super.onBeforeRender();
        }

    }



    private ScenarioRecorderFragment scenarioRecorder;

    public RecordingPage(PageParameters pageParameters) {

        scenarioItemSelector  = new ScenarioItemSelector();
        recordingId = new RecordingId(pageParameters.get("id").toString());

        add(new RecorderControlsFragment("recorderControls"));
        add(scenarioRecorder = new ScenarioRecorderFragment("recordedScenario"));

        scenarioRecorder.setOutputMarkupId(true);
    }

}
