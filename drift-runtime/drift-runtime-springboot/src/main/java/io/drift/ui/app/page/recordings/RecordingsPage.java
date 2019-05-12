package io.drift.ui.app.page.recordings;

import io.drift.ui.app.flux.RecordingActions;
import io.drift.ui.app.component.popup.Popup;
import io.drift.ui.app.page.layout.MainLayout;
import io.drift.ui.app.page.recording.RecordingPage;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import static io.drift.ui.infra.WicketUtil.ajaxLink;

@MountPath("recordings")
public class RecordingsPage extends MainLayout {

    private Popup createRecordingPopup;

    @SpringBean
    private RecordingActions recordingActions;

    class RecordingFormFragment extends Fragment {

        public RecordingFormFragment(String id) {
            super(id, "recordingFormFragment", RecordingsPage.this);
            add(ajaxLink("save", target -> {
                String recordingId = recordingActions.create().getId().getId();
                setResponsePage(RecordingPage.class, new PageParameters().add("id", recordingId));
            }));
        }

    }

    class RecordingsControlsFragment extends Fragment {

        public RecordingsControlsFragment(String id) {
            super(id, "recordingsControlsFragment", RecordingsPage.this);
            add(ajaxLink("newRecording", target -> {
                createRecordingPopup.show(target);
            }));

        }
    }

    public RecordingsPage() {
        add(new RecordingsControlsFragment("recordingsControls"));
        add(createRecordingPopup = new Popup("createRecordingPopup"));
        createRecordingPopup.setContent(new RecordingFormFragment(createRecordingPopup.getContentId()));
    }

}
