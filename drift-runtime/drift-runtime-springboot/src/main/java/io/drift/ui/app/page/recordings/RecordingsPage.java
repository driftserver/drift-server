package io.drift.ui.app.page.recordings;

import io.drift.ui.app.flux.EnvironmentDTO;
import io.drift.ui.app.flux.RecordingActions;
import io.drift.ui.app.flux.SystemStore;
import io.drift.ui.app.page.layout.MainLayout2;
import io.drift.ui.app.page.recording.RecordingPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serializable;
import java.util.List;

import static io.drift.ui.infra.WicketUtil.ajaxLink;

@MountPath("recordings")
public class RecordingsPage extends MainLayout2 {

    @SpringBean
    private RecordingActions recordingActions;

    @SpringBean
    private SystemStore systemStore;




    class CreateRecordingModal extends Fragment {

        class FormData implements Serializable {
            EnvironmentDTO environment;
        }

        private FormData formData = new FormData();

        CreateRecordingModal(String id) {
            super(id, "createRecordingModalFragment", RecordingsPage.this);
            Form form = new StatelessForm<FormData>("form", new CompoundPropertyModel<>(formData)) {
                @Override
                protected void onSubmit() {
                    String recordingId = recordingActions.create(formData.environment).getId().getId();
                    setResponsePage(RecordingPage.class, new PageParameters().add("id", recordingId));
                }
            };
            form.add(new DropDownChoice<>("environment", systemStore.getEnvironments(), new ChoiceRenderer<>("displayName", "key"))) ;
            add(form);
        }

    }

    class RecordingsControlsFragment extends Fragment {
        RecordingsControlsFragment(String id) {
            super(id, "recordingsControlsFragment", RecordingsPage.this);
        }
    }

    public RecordingsPage() {
        add(new RecordingsControlsFragment("recordingsControls"));
        add(new CreateRecordingModal("createRecordingModal"));
    }

}
