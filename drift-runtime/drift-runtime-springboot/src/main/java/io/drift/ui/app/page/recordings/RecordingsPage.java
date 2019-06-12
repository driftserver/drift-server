package io.drift.ui.app.page.recordings;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import io.drift.core.recording.RecordingId;
import io.drift.core.system.EnvironmentKey;
import io.drift.ui.app.flux.EnvironmentDTO;
import io.drift.ui.app.flux.RecordingActions;
import io.drift.ui.app.flux.RecordingStore;
import io.drift.ui.app.flux.SystemStore;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.app.page.layout.MainLayout2;
import io.drift.ui.app.page.recording.RecordingPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.Serializable;
import java.util.List;

import static io.drift.ui.infra.WicketUtil.*;

@MountPath("recordings")
public class RecordingsPage extends MainLayout {

    @SpringBean
    private RecordingActions recordingActions;

    @SpringBean
    private SystemStore systemStore;

    @SpringBean
    private RecordingStore recordingStore;

    class RecordingsFragment extends Fragment {
        RecordingsFragment(String id) {
            super(id, "recordingsFragment", RecordingsPage.this);
            add(listView("recordings", recordingStore.getRecordingSummaries(), (item)-> {
                Link select = ajaxLink("select", target -> {
                    navigateToRecording(item.getModelObject().getRecordingId());
                });
                item.add(select);
                select.add(label("name", item.getModelObject().getLabel()));
            }));

        }
    }

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
                    navigateToRecording(recordingActions.create(new EnvironmentKey(formData.environment.getKey())).getId());
                }

            };
            form.add(new DropDownChoice<>("environment", systemStore.getEnvironments(), new ChoiceRenderer<>("displayName", "key"))) ;
            add(form);
        }

    }

    private void navigateToRecording(RecordingId recordingId) {
        setResponsePage(RecordingPage.class, new PageParameters().add("id", recordingId.getId()));
    }

    class RecordingsControlsFragment extends Fragment {
        RecordingsControlsFragment(String id) {
            super(id, "recordingsControlsFragment", RecordingsPage.this);
        }
    }

    public RecordingsPage() {
        add(new RecordingsControlsFragment("recordingsControls"));
        add(new RecordingsFragment("recordings"));
        add(new CreateRecordingModal("createRecordingModal"));
    }

}
