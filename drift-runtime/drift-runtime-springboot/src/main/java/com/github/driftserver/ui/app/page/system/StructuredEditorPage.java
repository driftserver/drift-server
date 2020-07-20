package com.github.driftserver.ui.app.page.system;


import com.github.driftserver.ui.app.component.editor.execution.StructuredEditorState;
import com.github.driftserver.ui.app.flux.systemdescription.SystemEditor;
import com.github.driftserver.ui.app.page.layout.MainLayout2;
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
