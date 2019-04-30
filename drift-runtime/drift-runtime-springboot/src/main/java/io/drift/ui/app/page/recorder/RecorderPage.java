package io.drift.ui.app.page.recorder;

import io.drift.ui.app.page.layout.MainLayout;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.wicketstuff.annotation.mount.MountPath;

import static io.drift.ui.infra.WicketUtil.ajaxLink;

@MountPath("recorder")
public class RecorderPage extends MainLayout {

    class RecorderControlsFragment extends Fragment {

        public RecorderControlsFragment(String id) {
            super(id, "recorderControlsFragment", RecorderPage.this);
            add(ajaxLink("startRecording", target -> {
                 System.out.println("start recording");
                    //recorderActions.startRecording();
                    //target.add(scenarioRecorder);
            }));
            add(ajaxLink("takeSnapshot", target -> {
                System.out.println("take snapshot");
                //recorderActions.startRecording();
                //target.add(scenarioRecorder);
            }));
            add(ajaxLink("stopRecording", target -> {
                System.out.println("stop recording");
                //recorderActions.startRecording();
                //target.add(scenarioRecorder);
            }));

        }
    }

    public RecorderPage() {
        add(new RecorderControlsFragment("recorderControls"));
        add(new Label("recordedScenario", "[recordedScenario]"));
    }

}
