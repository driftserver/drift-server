package io.drift.ui.app.page.system;


import io.drift.ui.app.component.editor.execution.StructuredEditorState;
import io.drift.ui.app.flux.systemdescription.SystemEditor;
import io.drift.ui.app.page.layout.MainLayout2;
import org.apache.wicket.Component;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

@MountPath("se")
public class StructuredEditorPage extends MainLayout2 {

    StructuredEditorState editorState;

    @SpringBean
    SystemEditor systemEditor;

    Component editorComponent;

    public StructuredEditorPage() {
        add(systemEditor.render("editor", systemEditor.getEditorStructure().getRootElement()));
    }


}
