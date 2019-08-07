package io.drift.ui.app.page.recording;

import io.drift.core.recording.*;
import io.drift.ui.app.component.stacktrace.ProblemDescriptionListComponent;
import io.drift.ui.app.flux.recording.RecordingActions;
import io.drift.ui.app.flux.recording.RecordingStore;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.config.WicketComponentRegistry;
import io.drift.ui.infra.ListSelector;
import io.drift.ui.infra.Selector;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.LambdaModel;
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
    private final RecorderControlsFragment recorderControls;

    class ScenarioItemSelector extends Selector<Selector> implements Serializable {

        static final String INITIAL_STATE = "initial state";
        static final String SCENARIO_STEP = "scenario step";
        static final String FINAL_STATE = "final state";

        private String selection;
        private ScenarioStepSelector scenarioStepSelector = new ScenarioStepSelector(this);
        private SystemStateSubSystemSelector systemStateSubSystemSelector = new SystemStateSubSystemSelector(this);

        public ScenarioItemSelector() {
            super(null);
            setFocus(true);
        }

        public void selectInitialState() {
            if (recorderStore.getRecording(recordingId).getInitialState() != null) {
                selection = INITIAL_STATE;
                systemStateSubSystemSelector.setMaxIdx(recorderStore.getRecording(recordingId).getInitialState().getOrderedSubSystemKeys().size());
                systemStateSubSystemSelector.selectFirstSubSystem();
            }
        }

        public boolean isInitialStateSelected() {
            return INITIAL_STATE.equals(selection);
        }

        public void selectFinalState() {
            selection = FINAL_STATE;
            systemStateSubSystemSelector.setMaxIdx(recorderStore.getRecording(recordingId).getFinalstate().getOrderedSubSystemKeys().size());
            systemStateSubSystemSelector.selectFirstSubSystem();
        }

        public boolean isFinalStateSelected() {
            return FINAL_STATE.equals(selection);
        }

        public void selectScenarioStep(Integer idx) {
            selection = SCENARIO_STEP;
            scenarioStepSelector.select(idx);
            scenarioStepSelector.setMaxIdx(recorderStore.getRecording(recordingId).getSteps().size()-1);
        }

        public boolean isScenarioStepSelected() {
            return SCENARIO_STEP.equals(selection);
        }

        public boolean isScenarioItemSelected() {
            return selection != null;
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

        public void selectLast() {
            Recording recording = recorderStore.getRecording(recordingId);
            if (recording.getSteps().size() > 0) {
                selectScenarioStep(recording.getSteps().size() - 1);
            } else if(recording.getInitialState()!=null) {
                selectInitialState();
            }
        }

        public void handleArrowNavigation(String keyCode) {
            if (!hasFocus()) {
                if (isInitialStateSelected() || isFinalStateSelected()) {
                    systemStateSubSystemSelector.handleArrowNavigation(keyCode);
                }
            }
            else if (KEY_UP.equals(keyCode)) {
                if (isScenarioStepSelected()) {
                    if (scenarioStepSelector.getSelection() > 0) {
                        scenarioStepSelector.decrease();
                    } else {
                        selectInitialState();
                    }
                } else if (isFinalStateSelected()){
                    selectLast();
                }

            } else if (KEY_DOWN.equals(keyCode)) {
                if (isScenarioStepSelected()) {
                    if (scenarioStepSelector.getSelection() < scenarioStepSelector.getMaxIdx()) {
                        scenarioStepSelector.increase();
                    } else {
                        selectFinalState();
                    }
                } else if (isInitialStateSelected()){
                    selectScenarioStep(0);
                }

            } else if (KEY_RIGHT.equals(keyCode)) {
                if (isInitialStateSelected() || isFinalStateSelected()) {
                    systemStateSubSystemSelector.setFocus(true);
                    setFocus(false);
                }

            }

        }

    }

    class SystemInteractionsSelector extends ListSelector<ScenarioStepSelector> {
        public SystemInteractionsSelector(ScenarioStepSelector parentselector) {
            super(parentselector);
        }

        public SystemInteraction getSelectedSystemInteraction() {
            return getParentselector().getSelectedScenarioItem().getSystemInteractions().get(getSelection());
        }

        public SubSystemDescription getSelectedSubSystem() {
            return getParentselector().getParentselector().getSubSystemDescription(getSelectedSystemInteraction().getSubSystem());
        }
    }

    class ScenarioStepSelector extends ListSelector<ScenarioItemSelector> {

        private SystemInteractionsSelector systemInteractionsSelector = new SystemInteractionsSelector(this);

        public ScenarioStepSelector(ScenarioItemSelector parentSelector) {
            super(parentSelector);
        }

        public RecordingStep getSelectedScenarioItem() {
            return recorderStore.getRecordingStep(recordingId, getSelection());
        }

        public SystemInteractionsSelector getSystemInteractionsSelector() {
            return systemInteractionsSelector;
        }
    }

    class SystemStateSubSystemSelector extends ListSelector<ScenarioItemSelector> {

        protected SystemStateSubSystemSelector() {
        }


        public SystemStateSubSystemSelector(ScenarioItemSelector parentSelector) {
            super(parentSelector);
        }

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

        public void selectFirstSubSystem() {
            if (getParentselector().getSelectedSystemState().getOrderedSubSystemKeys().size() > 0) {
                select(0);
            }
        }

        public void handleArrowNavigation(String keyCode) {
            if (KEY_LEFT.equals(keyCode)) {
                setFocus(false);
                getParentselector().setFocus(true);
            } else if (KEY_UP.equals(keyCode)) {
                decrease();
            } else if (KEY_DOWN.equals(keyCode)) {
                increase();
            }
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
                select.add(createSubSystemStateSummary("selectedSubSystemSummary", subSystemSelector.getSelectedSubSystemState(), subSystemSelector.getSubSystemDescription()));

                Label selected = label("selected");
                if (subSystemSelector.hasFocus()) addClass(selected, "btn-outline-primary");
                selected.setVisible(subSystemSelector.isSelected() && subSystemSelector.getSelection() == item.getModelObject());
                select.add(selected);

            }));
            if (subSystemSelector.isSelected()) {
                add(createSubSystemStateDetail("selectedSubSystemState", subSystemSelector.getSelectedSubSystemState(), subSystemSelector.getSubSystemDescription()));
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

    private Component createSubSystemStateSummary(String id, SubSystemState subSystemState, SubSystemDescription subSystemDescription) {
        return registry.render(id, subSystemState, SubSystemStateSummaryView.class, subSystemDescription);
    }

    public class ScenarioFragment extends Fragment {


        public ScenarioFragment(String id) {
            super(id, "scenarioFragment", RecordingPage.this);

            WebMarkupContainer noSnapshots, scenarioItems;

            add(noSnapshots = div("noSnapshots"));
            noSnapshots.setVisible(recorderStore.getRecording(recordingId).getInitialState()==null);

            add(scenarioItems = div("scenarioItems"));
            scenarioItems.setVisible(recorderStore.getRecording(recordingId).getInitialState()!=null);

            {
                Link initialState = ajaxLink("selectInitialState", target -> {
                    scenarioItemSelector.selectInitialState();
                    target.add(scenarioRecorder);
                });

                Label selected = label("selected");
                if (scenarioItemSelector.hasFocus()) addClass(selected, "btn-outline-primary");
                selected.setVisible(scenarioItemSelector.isInitialStateSelected());

                scenarioItems.add(initialState);
                initialState.add(selected);
            }

            {
                Link finalState = ajaxLink("selectFinalState", target -> {
                    scenarioItemSelector.selectFinalState();
                    target.add(scenarioRecorder);
                });

                Label selected = label("selected");
                if (scenarioItemSelector.hasFocus()) addClass(selected, "btn-outline-primary");
                selected.setVisible(scenarioItemSelector.isFinalStateSelected());

                scenarioItems.add(finalState);
                finalState.add(selected);
            }

            scenarioItems.add(listView("steps", indices(recorderStore.getRecording(recordingId).getSteps()), stepItem -> {
                Integer idx = stepItem.getModelObject();

                Link<Void> step = ajaxLink("step", target -> {
                    scenarioItemSelector.selectScenarioStep(idx);
                    target.add(scenarioRecorder);
                });

                Label selected = label("selected");
                if (scenarioItemSelector.hasFocus()) addClass(selected, "btn-outline-primary");
                selected.setVisible(scenarioItemSelector.isScenarioStepSelected() && idx.equals(scenarioItemSelector.getScenarioStepSelector().getSelection()));

                stepItem.add(step);
                step.add(label("title", recorderStore.getRecording(recordingId).getSteps().get(idx).getTitle()));
                step.add(selected);
            }));
        }
    }



    class ActionResultFragment extends Fragment {

        public ActionResultFragment(String id) {
            super(id, "actionResultFragment", RecordingPage.this);

            add(label("problemCount", () -> {
                int count = recorderStore.getActionResult(recordingId).getProblemDescriptions().size();
                return "" + count + " problems";
            }));

            add(new ProblemDescriptionListComponent("problemDescriptions", () ->
                recorderStore.getActionResult(recordingId).getProblemDescriptions()
            ));
        }

    }

    class RecorderControlsFragment extends Fragment {

        private ActionResultFragment actionResult;

        public RecorderControlsFragment(String id) {
            super(id, "recorderControlsFragment", RecordingPage.this);

            add(ajaxLink("takeSnapshot", target -> {
                recordingActions.takeSnapshot(recordingId);
                scenarioItemSelector.selectLast();
                refresh(target);
            }));
            add(actionResult = new ActionResultFragment("actionResult"));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            int problemCount = recorderStore.getActionResult(recordingId).getProblemDescriptions().size();
            actionResult.setVisible(problemCount > 0);
        }

        private void refresh(AjaxRequestTarget target) {
            target.add(scenarioRecorder, recorderControls);
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

            scenarioItemSelector.selectInitialState();
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

        scenarioItemSelector = new ScenarioItemSelector();
        recordingId = new RecordingId(pageParameters.get("id").toString());

        add(recorderControls = new RecorderControlsFragment("recorderControls"));
        add(scenarioRecorder = new ScenarioRecorderFragment("recordedScenario"));

        recorderControls.setOutputMarkupId(true);
        scenarioRecorder.setOutputMarkupId(true);
        add(arrowkeyBehavior((keyCode, target) -> {
            scenarioItemSelector.handleArrowNavigation(keyCode);
            target.add(scenarioRecorder);
        }));

        add(unloadTrackingBehaviour("unloadTracker", target -> {
            recordingActions.closeSession(recordingId);
        }));

    }

}
